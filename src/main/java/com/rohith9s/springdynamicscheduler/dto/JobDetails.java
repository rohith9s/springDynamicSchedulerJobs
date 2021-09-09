package com.rohith9s.springdynamicscheduler.dto;

import lombok.Data;

/**
 * 
 * @author Rohith Varala
 *
 */
@Data
public class JobDetails {
	private String jobName;
	private String jobDesc;
	private String cronExpression;
}