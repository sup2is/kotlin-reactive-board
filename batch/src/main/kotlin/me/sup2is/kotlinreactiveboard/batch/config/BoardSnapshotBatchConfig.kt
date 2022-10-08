package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.model.BoardSnapshot
import me.sup2is.kotlinreactiveboard.domain.repository.BlockedBoardRepository
import me.sup2is.kotlinreactiveboard.domain.repository.BoardSnapshotRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.data.MongoItemReader
import org.springframework.batch.item.data.MongoItemWriter
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDateTime

@Configuration
class BoardSnapshotBatchConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val mongoTemplate: MongoTemplate
) {

    @Bean
    fun boardSnapshotStep(): TaskletStep {
        return stepBuilderFactory.get("boardSnapshotStep")
            .chunk<Board, BoardSnapshot>(5)
            .reader(boardSnapshotItemReader("", ""))
            .processor(boardSnapshotProcessor())
            .writer(boardSnapshotItemWriter())
            .build()
    }

    @Bean
    fun boardSnapshotJob(): Job {
        return jobBuilderFactory.get("boardSnapshotJob")
            .start(boardSnapshotStep())
            .build()
    }

    @Bean
    @StepScope
    fun boardSnapshotItemReader(
        @Value("#{jobParameters[fromDate]}") fromDate: String, // yyyymmdd
        @Value("#{jobParameters[toDate]}") toDate: String // yyyymmdd
    ): MongoItemReader<Board> {
        return MongoItemReader<Board>().apply {
            this.setTemplate(mongoTemplate)
            this.setTargetType(Board::class.java)
            this.setQuery(
                Query(
                    Criteria.where("createAt").lt(fromDate).gte(toDate)
                )
            )
        }
    }

    @Bean
    fun boardSnapshotItemWriter(): MongoItemWriter<BoardSnapshot> {
        return MongoItemWriterBuilder<BoardSnapshot>()
            .template(mongoTemplate)
            .build()
    }

    @Bean
    fun boardSnapshotProcessor(): ItemProcessor<Board, BoardSnapshot> {
        return ItemProcessor {
            BoardSnapshot(it)
        }
    }
}