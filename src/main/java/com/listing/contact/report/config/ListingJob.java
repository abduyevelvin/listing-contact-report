package com.listing.contact.report.config;

import com.listing.contact.report.model.Listing;
import com.listing.contact.report.processor.DBListingLogProcessor;
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
public class ListingJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${listing.file.path}")
    private String path;

    public ListingJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean("listingsJob")
    public Job listingsJob() {
        return jobBuilderFactory
                .get("listingsJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<Listing, Listing>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<Listing, Listing> processor() {
        return new DBListingLogProcessor();
    }

    @Bean
    public FlatFileItemReader<Listing> reader() {
        FlatFileItemReader<Listing> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new PathResource(FileReadUtil.getUri(path)));
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter<Listing> writer() {
        JdbcBatchItemWriter<Listing> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO LISTING (ID, MAKE, PRICE, MILEAGE, SELLER_TYPE) " +
                "VALUES ( :id, :make, :price, :mileage, :sellerType )");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    @Bean
    public LineMapper<Listing> lineMapper() {
        DefaultLineMapper<Listing> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Listing> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        lineTokenizer.setNames("id", "make", "price", "mileage", "sellerType");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4);
        fieldSetMapper.setTargetType(Listing.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
