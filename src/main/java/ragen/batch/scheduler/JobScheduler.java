package ragen.batch.scheduler;


import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("prod")
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    Environment env;

    /*
        Shedlock Redis 멀티서버 스케쥴 사용 (2023.11.29)
        - @SchedulerLock 사용.
        - 운영 환경에서 서버 인스턴스가 n개로 늘어도 스케쥴러는 한 인스턴스에서만 작동하도록.
        - 참고링크: https://ssukssuk2papa.tistory.com/3
     */

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        // 인스턴스 n대가 해당 lock을 사용하기 원할 경우 두 인스턴스 모두 같은 lockEnv 값을 가지면 됨.
        String lockEnv = env.getProperty("spring.profiles.active");
        return new RedisLockProvider(connectionFactory, lockEnv);
    }

    //상담 데이터 송신(상담관리솔루션->휴맥스EV서버)
    //휴맥스 api(HUMAX_CNSL_DATA_RCV) 실패된 목록중 재전송목적
    //계속 실패나면 5번까지만 재전송하고 그대로 놔둠(휴맥스에서 요구사항임)
    @Scheduled(cron = "0 */10 * * * *") // 10분마다 실행
    @SchedulerLock(name = "runHumaxCnslDataRcvFailTask", lockAtLeastFor = "4m", lockAtMostFor = "9m")
    public void runHumaxCnslDataRcvFailTreatementYn() {
        try {
            LockAssert.assertLocked();
            //humaxCnslService.setHumaxCnslDataRcvBatchTreatementYn();
        } catch (Exception e) {
            log.error("상담 데이터 송신(상담관리솔루션->휴맥스EV서버) 실행 중 오류 발생", e);
        }
    }

    /*
        동일한 scheduled 함수 중복 실행시 다른 쪽 인스턴스에는 락 적용.
        최소 3초 ~ 최대 9초 동안. (스케쥴 반복 주기는 10초 - 아래)
     */
    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 실행
    @SchedulerLock(name = "pushCallsWaitingTask", lockAtLeastFor = "4s", lockAtMostFor = "9s")
    public void pushInCallsWaiting() {
        try {
            LockAssert.assertLocked();
            //cnslMstService.pushInCallsWaiting();
        } catch (Exception e) {
            log.error("BP 대기중상담 push 실행중 오류발생", e);
        }
    }

}