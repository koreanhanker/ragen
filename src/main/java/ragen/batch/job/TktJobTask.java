package ragen.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TktJobTask {

    final JobRepository jobRepository;
    final PlatformTransactionManager batchTransactionManager;

//    @Autowired
//    private TkTMstService tktMstService;
//    private TkTDTO reqDTO;

    @Bean
    public Job tktAssign(Step step1) {
        return new JobBuilder("tktAssign", jobRepository)
                .start(step1)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    //tktMstService.setAutoTktAlcl();
                    return RepeatStatus.FINISHED;}, batchTransactionManager)
                .build();
    }
}
