package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	private void Sysout() {
		// TODO Auto-generated method stub

	}
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
		.tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("First step");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
		.tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("second step");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3")
		.tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("third step");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	
	@Bean
	public Job firstJob1() {
		return jobBuilderFactory.get("firstJob1") 
				.start(step1())
				.next(step2())
				.build();
	}

	//job transition
        //remote
	//local
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("firstJob") 
				.start(step1())
			    .on("COMPLETED").to(step2())
			    .from(step2()).on("COMPLETED").to(step3())
			    .from(step3()).end()
				.build();
	}

	//job transition with fail step3 is not executed
	/*
		@Bean
		public Job firstJob() {
			return jobBuilderFactory.get("firstJob") 
					.start(step1())
				    .on("COMPLETED").to(step2())
				    .from(step2()).on("COMPLETED").fail()
				    .from(step3()).end()
					.build();
		}
		*/
		//job transition with stop and restart
	/*
				@Bean
				public Job firstJob() {
					return jobBuilderFactory.get("firstJob") 
							.start(step1())
						    .on("COMPLETED").to(step2())
						    .from(step2()).on("COMPLETED").stopAndRestart(step3())
						    .from(step3()).end()
							.build();
				}
	*/
}
