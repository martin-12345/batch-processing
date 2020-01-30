package com.example.batchprocessing;

import java.text.ParseException;

public class FilePersonMapper implements RowMapper<Person> {

	private String delim;

	public FilePersonMapper(String delim) {
		this.delim = delim;
	}

	@Override
	public Person mapRow(String row) throws Exception {
		Person p = null;
		try {
			String[] parts = row.split(delim);
			p = new Person(parts[0], parts[1], parts[2]);
		} catch (Exception e) {
			throw new ParseException("not able to create a Person object", 0);
		}
		return p;
	}

}
