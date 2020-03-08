package com.example.batchprocessing;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class JobJVMArgsValidator implements JobParametersValidator {
	private String dir;
	
	public JobJVMArgsValidator() {}

	public  JobJVMArgsValidator(String dir) {
		this.dir = dir;
	}

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {

		dir = parameters.getString("run.dir");
		if (dir==null) {
			throw new JobParametersInvalidException("run.dir is null");
		}
		
	}

}
