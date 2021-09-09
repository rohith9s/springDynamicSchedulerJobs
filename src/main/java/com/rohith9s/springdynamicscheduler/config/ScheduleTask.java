package com.rohith9s.springdynamicscheduler.config;

import java.time.LocalDateTime;

import com.rohith9s.springdynamicscheduler.dto.JobDetails;
/**
 * 
 * @author Rohith Varala
 * 
 * @see {@link SchedulerConfig#initializeSchedulers()}
 *
 */
public class ScheduleTask implements Runnable {

	private JobDetails jobDetails;

	public ScheduleTask(JobDetails jobDetails) {
		this.jobDetails = jobDetails;
	}

	/**
	 * This method get invoked with configured cron expression intervals
	 */
	@Override
	public void run() {
		logMessage();
	}

	private void logMessage() {
		System.out.println("*************Job details start*************");
		System.out.println("jobName:" + jobDetails.getJobName());
		System.out.println("Current DateTime:" + LocalDateTime.now());
		System.out.println("*************Job Details end*************");
	}
}
