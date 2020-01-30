package com.example.batchprocessing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.data.MongoItemWriter;

public class MockMongoItemWriter<T> extends MongoItemWriter<T> {

	private static final Logger log = LoggerFactory.getLogger(MockMongoItemWriter.class);

	@Override
	public void write(List<? extends T> items) throws Exception {
		//items.forEach(this::doSomething);
		log.info("{} records", items.size());
	}

	@Override
	public void setCollection(String collection) {
		log.info("Collection is {}", collection);
	}

	@SuppressWarnings("unused")
	private void doSomething(T  row) {
		if (log.isInfoEnabled()) {
			log.info(row.toString());
		}
	}

}
