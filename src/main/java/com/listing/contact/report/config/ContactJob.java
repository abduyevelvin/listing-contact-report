package com.listing.contact.report.config;

import com.listing.contact.report.model.Contact;
import com.listing.contact.report.processor.DBContactLogProcessor;
import com.listing.contact.report.util.FileReadUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class ContactJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${contact.file.path}")
    private String path;

    public ContactJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean("contactsJob")
    public Job contactsJob() {
        return jobBuilderFactory
                .get("contactsJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean("contactJobStep")
    public Step step() {
        return stepBuilderFactory
                .get("contactJobStep")
                .<Contact, Contact>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean("contactProcessor")
    public ItemProcessor<Contact, Contact> processor() {
        return new DBContactLogProcessor();
    }

    @Bean("contactReader")
    public FlatFileItemReader<Contact> reader() {
        FlatFileItemReader<Contact> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new PathResource(FileReadUtil.getUri(path)));
        return itemReader;
    }

    @Bean("addressWriter")
    public JdbcBatchItemWriter<Contact> writer() {
        JdbcBatchItemWriter<Contact> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO CONTACT ( LISTING_ID, CONTACT_DATE) VALUES ( :listingId, :contactDate )");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    @Bean("contactLineMapper")
    public LineMapper<Contact> lineMapper() {
        DefaultLineMapper<Contact> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Contact> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        lineTokenizer.setNames("listingId", "contactDate");
        lineTokenizer.setIncludedFields(0, 1);
        fieldSetMapper.setTargetType(Contact.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
