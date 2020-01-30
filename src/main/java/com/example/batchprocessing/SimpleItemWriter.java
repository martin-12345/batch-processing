package com.example.batchprocessing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class SimpleItemWriter<T> implements ItemWriter<String> {

	private static final Logger log = LoggerFactory.getLogger(SimpleItemWriter.class);

	@Override
	public void write(List<? extends String> items) throws Exception {
		items.forEach(this::doSomething);
		if (log.isInfoEnabled()) {
			log.info("{} Items", items.size());
		}
	}
	
	private void doSomething(String  row) {
		if (log.isInfoEnabled()) {
			log.info(row);
		}
	}
}
