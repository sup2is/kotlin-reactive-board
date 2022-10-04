package me.sup2is.kotlinreactiveboard.batch.config

import org.junit.jupiter.api.Test
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.test.context.ContextConfiguration

@SpringBatchTest
@ContextConfiguration(classes = [BoardSnapshotBatchConfig::class])
@EnableBatchProcessing
class BoardSnapshotBatchConfigTest {

    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Test
    fun test() {

    }

}