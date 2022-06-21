package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : ReactiveMongoRepository<Board, Long>
