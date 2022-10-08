package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.model.BoardSnapshot
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
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM")

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
            .reader(boardSnapshotItemReader(""))
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
        @Value("#{jobParameters[targetDate]}") targetDate: String, // yyyyMM
    ): MongoItemReader<Board> {

        val yearMonth = YearMonth.parse(targetDate, dateTimeFormatter)

        val from = yearMonth.atDay(1)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
        val to = yearMonth.atEndOfMonth()
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
        return MongoItemReader<Board>().apply {
            this.setTemplate(mongoTemplate)
            this.setTargetType(Board::class.java)
            this.setQuery(
                Query(
                    Criteria.where("createAt").gte(from).lt(to)
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