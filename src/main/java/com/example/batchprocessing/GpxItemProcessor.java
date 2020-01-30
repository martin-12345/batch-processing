package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class GpxItemProcessor implements ItemProcessor<Person, Person> {

	private static final Logger log = LoggerFactory.getLogger(GpxItemProcessor.class);

	@Override
	public Person process(Person arg0) throws Exception {
		log.info("Processing: "+arg0);
		return arg0;
	}
}
