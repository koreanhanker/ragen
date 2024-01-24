package ragen.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ragen.example.dto.ExampleDTO;

import java.util.HashMap;
import java.util.Map;
//@EnableBatchProcessing
@Configuration
@Slf4j
public class BatchJobTask
{

    final JobRepository jobRepository;
    final PlatformTransactionManager batchTransactionManager;

    @Autowired
    private SqlSessionTemplate batchSqlSessionTemplate;

    private static final int BATCH_SIZE = 3;

    public BatchJobTask(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        this.jobRepository = jobRepository;
        this.batchTransactionManager = batchTransactionManager;
    }

    /**
     * Job which contains multiple steps
     */

    @Bean
    public Job firstJob() {
        log.info("first Job");
        return new JobBuilder("first job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .next(taskletStep())
                .build();
    }

    @Bean
    public Step taskletStep () {
        log.info("tasklet step");
        return new StepBuilder("tasklet step", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is first tasklet step");
                    log.info("SEC = {}", chunkContext.getStepContext().getStepExecutionContext());
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager).build();
    }

    @Bean
    public Step chunkStep() {
        log.info("chunk step");
        return new StepBuilder("chunk step", jobRepository)
                .<ExampleDTO, ExampleDTO>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<ExampleDTO> reader() {
        log.info("reader");
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("id", "T001");

        return new MyBatisPagingItemReaderBuilder<ExampleDTO>()
                .pageSize(10)
                .sqlSessionFactory(batchSqlSessionTemplate.getSqlSessionFactory())
                // Mapper안에서도 Paging 처리 시 OrderBy는 필수!
                .queryId("TEST.selectTestList")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    public ItemProcessor<ExampleDTO, ExampleDTO> processor(){
        log.info("processor");
        return new ItemProcessor<ExampleDTO, ExampleDTO>() {
            @Override
            public ExampleDTO process(ExampleDTO exampleDTO) throws Exception {

                //1000원 추가 적립
                exampleDTO.setName("오늘업데이트");

                return exampleDTO;
            }
        };
    }
    @Bean
    public MyBatisBatchItemWriter<ExampleDTO> writer() {
        log.info("writer");
        return new MyBatisBatchItemWriterBuilder<ExampleDTO>()
                //.assertUpdates(false)
                .sqlSessionFactory(batchSqlSessionTemplate.getSqlSessionFactory())
                .statementId("TEST.updateTestInfo")
                .build();
    };
}
