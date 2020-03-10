package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Value("${mongo.tracker.collection}")
	private String collection;
	@Value("${mongo.tracker.chunk}")
	private int chunkSize;
	@Value("#{environment.dbusername}")
	String name;

	@Bean(destroyMethod = "")
	public ItemReader<Person> gpxReader(@Qualifier("publish-ds") DataSource datasource) {
		System.out.println("name="+name);
		JdbcCursorItemReader<Person> databaseReader = new JdbcCursorItemReader<>();

		databaseReader.setDataSource(datasource);
		databaseReader.setSql(PersonMapper.QUERY_FIND_TRACKS);
		databaseReader.setRowMapper(new PersonMapper());

		return databaseReader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new GpxItemProcessor();
	}

	@Bean
	public ItemWriter<Person> writer() {
		MockMongoItemWriter<Person> writer = new MockMongoItemWriter<>();
		writer.setTemplate(mongoTemplate);
		writer.setCollection(collection);
		return writer;
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1, JobParametersValidator validator) {
		return jobBuilderFactory.get("importUserJob")
				.validator(validator)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(ItemWriter<Person> writer, ItemReader<Person> reader, ItemProcessor<Person, Person> processor) {
		return stepBuilderFactory.get("step1")
				.<Person, Person>chunk(chunkSize)
				.reader(reader)
				// .processor(processor)
				.writer(writer).build();
	}
	
	@Bean 
	public JobParametersValidator validator() {
		return new JobJVMArgsValidator( );
	}
}
