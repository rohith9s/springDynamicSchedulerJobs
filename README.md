# Spring Dynamic Scheduler Jobs

Spring makes life easier to schedule a job to run periodically. All we need to do is to put @Scheduled annotation above the method and provide the parameters such as fixedRate or cron expression.  

## Problem Statement inspired to dig above solution:
	Above feature does not support to change the fixedRate or cron expression on the fly & @Scheduled is not going to fulfill the requirement. As business needs frequent changes in cron expression which require new WAR deployment to make them available in environment. To avoid this, we need to load configuration from database where we configure metadata information like job name, cron expression, service name & etc. So that I can load data from DB & schedule the task accordingly. Whenever the value changes, the next execution time can also be changed but here am not considering to implement it. However it can be reloaded on server startup.
	

## Below is the core implementation classes:

```
   com.rohith9s.springdynamicscheduler.config.SchedulerConfig
   com.rohith9s.springdynamicscheduler.config.ScheduleTask
```

To Run application: Run it as a spring boot application & see the logs where it will log message periodically.

Thats it, spring dynamic scheduler jobs ready. Code is self explanatory & placed comments for better understanding.

