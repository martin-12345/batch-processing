package com.example.batchprocessing;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface RowMapper<T> {

	@Nullable
	T mapRow(String row) throws Exception;
}
