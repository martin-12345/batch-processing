package com.example.batchprocessing;

import java.io.Reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;

public class AzureBlobStoreFileItemReader<T> implements InitializingBean, ItemReader<T> {

	private RowMapper<T> mapper;
	private Reader in;
	private int off=0;
	private StringBuffer  row;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.in = getReader();
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		row = new StringBuffer();
		char[] buf = new char[1024];
		@SuppressWarnings("unused")
		int c = 0;
		while((c = in.read(buf, off++, 1)) > 0) {
			if(buf[0] == '\n') {
				// Map and eject row
				return mapper.mapRow(row.toString());
			} else  {
				row.append(buf[0]);
			}
		}
		if(row.length() > 0) {
			return mapper.mapRow(row.toString());
		}

		return null;
	}
	
	public void setRowMapper(RowMapper<T> mapper) {
		this.mapper = mapper;
	}

	
	/*
	 * This is where we use the input params to connect to Azure and get a connection
	 * to out blob
	 */
	public Reader getReader() {
		return null;
	}
}
