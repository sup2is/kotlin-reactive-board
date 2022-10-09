package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.batch.listener.JobCompletionNotificationListener
import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.time.YearMonth

@ActiveProfiles("dev")
@SpringBatchTest
@ContextConfiguration(classes = [BoardSnapshotBatchConfig::class])
@Import(BatchTestConfig::class, JobCompletionNotificationListener::class)
@DataMongoTest
@EnableAutoConfiguration
class BoardSnapshotBatchConfigTest {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Test
    fun test() {
        val targetDate = "202110"
        val board = makeBoard(targetDate)

        mongoTemplate.save(board)

        val jobParameters: JobParameters = JobParametersBuilder()
            .addString("targetDate", targetDate)
            .toJobParameters()

        val launchJob = jobLauncherTestUtils.launchJob(jobParameters)

        assertThat(launchJob.exitStatus).isEqualTo(ExitStatus.COMPLETED)

        val stepExecution = launchJob.stepExecutions.first()

        assertThat(stepExecution.readCount).isEqualTo(1)
        assertThat(stepExecution.writeCount).isEqualTo(1)
    }

    private fun makeBoard(targetDate: String): Board {

        val yearMonth = YearMonth.parse(targetDate, dateTimeFormatter)

        return Board().apply {
            this.createAt = yearMonth.atDay(15).atStartOfDay()
        }
    }

}