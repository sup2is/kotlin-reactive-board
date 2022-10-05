package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.domain.repository.BlockedBoardRepository
import me.sup2is.kotlinreactiveboard.domain.repository.BoardSnapshotRepository
import org.junit.jupiter.api.Test
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration

@SpringBatchTest
@ContextConfiguration(classes = [BoardSnapshotBatchConfig::class])
@Import(BatchTestConfig::class)
class BoardSnapshotBatchConfigTest {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @MockBean
    lateinit var blockedBoardRepository: BlockedBoardRepository

    @MockBean
    lateinit var boardSnapshotRepository: BoardSnapshotRepository

    @Test
    fun test() {
        val jobParameters: JobParameters = JobParametersBuilder()
            .toJobParameters()

        val launchJob = jobLauncherTestUtils.launchJob()
    }

}