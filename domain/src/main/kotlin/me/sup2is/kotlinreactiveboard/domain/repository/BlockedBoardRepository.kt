package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BlockedBoardRepository : MongoRepository<Board, Long> {
    fun findByCreateAtBetween(fromDate: LocalDateTime, toDate: LocalDateTime): List<Board>
}