package com.rohith9s.springdynamicscheduler.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.rohith9s.springdynamicscheduler.dto.JobDetails;

/**
 * 
 * @author Rohith Varala
 *
 * @see {@link ScheduleTask}
 */
@Configuration
@Component
public class SchedulerConfig {

	private static ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	private Map<String, ScheduledFuture<?>> scheduleFutures = new HashMap<>();

	/**
	 * init will execute once in a life time & initialize the scheduler.
	 */
	@PostConstruct
	public void init() {
		List<JobDetails> jobDetailsList = getJobDetails();
		int poolSize = 10;
		initializeTaskSchedulers(poolSize);
		initializeSchedulers(jobDetailsList);
		System.out.println("************Scheduler configuration completed************");
	}

	/**
	 * Load JobDetails either from Database, Props file or any other external
	 * source. Hard coded in the interest of time.
	 * 
	 * @return
	 */
	private List<JobDetails> getJobDetails() {
		List<JobDetails> jdList = new ArrayList<>();

		JobDetails jd1 = new JobDetails();
		jd1.setJobName("job1");
		jd1.setJobDesc("Executes every minute.");
		jd1.setCronExpression("0 */1 * ? * *");
		JobDetails jd2 = new JobDetails();
		jd2.setJobName("job2");
		jd2.setJobDesc("Executes every five minutes.");
		jd2.setCronExpression("0 */5 * ? * *");

		JobDetails jd3 = new JobDetails();
		jd3.setJobName("job3");
		jd3.setJobDesc("Executes every hour.");
		jd3.setCronExpression("0 0 */1 ? * *");

		jdList.add(jd1);
		jdList.add(jd2);
		jdList.add(jd3);
		return jdList;
	}

	/**
	 * initializeSchedulers - will create series of jobs with the configured cron
	 * expression
	 * 
	 * @param jobDetailsList
	 */
	private void initializeSchedulers(List<JobDetails> jobDetailsList) {
		ScheduledFuture<?> scheduleFuture;
		for (JobDetails jobDetail : jobDetailsList) {
			System.out.println("jobDetail:"+ jobDetail);
			scheduleFuture = taskScheduler.schedule(new ScheduleTask(jobDetail),
					new CronTrigger(jobDetail.getCronExpression()));
			scheduleFutures.put(jobDetail.getJobName(), scheduleFuture);
		}

	}

	/**
	 * initialize {@link ThreadPoolTaskScheduler} which will execute tasks
	 * asynchronously depending on the pool size.
	 * 
	 * @param poolSize
	 */
	private void initializeTaskSchedulers(int poolSize) {
		taskScheduler.setPoolSize(poolSize);
		taskScheduler.initialize();
	}

	/**
	 * This get's executed once in a life time before server shutdown.
	 */
	@PreDestroy
	public void destroy() {
		destroySchedulers();
	}

	/**
	 * destroy all the jobs before server shutdown
	 */
	private void destroySchedulers() {
		for (String batchJobName : scheduleFutures.keySet()) {
			ScheduledFuture<?> scheduledFuture = scheduleFutures.get(batchJobName);
			if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}
		}

	}
}
