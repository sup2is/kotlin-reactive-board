package me.sup2is.kotlinreactiveboard.batch.listener

import mu.KotlinLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class JobCompletionNotificationListener : JobExecutionListener {

    val logger = KotlinLogging.logger {}

    override fun beforeJob(jobExecution: JobExecution) {
        logger.info { "### beforeJob ###" }
    }

    override fun afterJob(jobExecution: JobExecution) {
        logger.info { "### afterJob ###" }
        logger.info { "### status : ${jobExecution.exitStatus} ###" }
        logger.info { "### endTime : ${jobExecution.endTime} ###" }
        logger.info { "### exception : ${jobExecution.failureExceptions} ###" }
        logger.info { "### jobParameters : ${jobExecution.jobParameters} ###" }
    }
}