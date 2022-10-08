package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.domain.repository.BlockedBoardRepository
import me.sup2is.kotlinreactiveboard.domain.repository.BoardSnapshotRepository
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
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("dev")
@SpringBatchTest
@ContextConfiguration(classes = [BoardSnapshotBatchConfig::class])
@Import(BatchTestConfig::class)
@DataMongoTest
@EnableAutoConfiguration
class BoardSnapshotBatchConfigTest {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Test
    fun test() {
        val jobParameters: JobParameters = JobParametersBuilder()
            .toJobParameters()

        val launchJob = jobLauncherTestUtils.launchJob()
    }

}