package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BoardRepository : ReactiveMongoRepository<Board, Long>
