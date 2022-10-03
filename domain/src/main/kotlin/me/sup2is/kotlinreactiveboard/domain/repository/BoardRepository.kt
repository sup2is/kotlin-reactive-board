package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface BoardRepository : ReactiveMongoRepository<Board, Long> {
    fun findByCreateAtBetween(fromDate: LocalDateTime, toDate: LocalDateTime): Flux<Board>
}
