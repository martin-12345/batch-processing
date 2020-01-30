package com.example.batchprocessing;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonMapper implements RowMapper<Person> {

	public static final String QUERY_FIND_TRACKS = "SELECT id, name, created_on FROM TEST_DATA";

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		/*
		 * The processing here extracts data from the result set passed which is determined
		 * by the query.
		 */
		String owner = rs.getString(1);
		String name = rs.getString(2);
		String date = rs.getString(3);
		return new Person(name, owner,date);
	}
}
