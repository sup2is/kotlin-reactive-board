package me.sup2is.kotlinreactiveboard.receiver.service

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.repository.BoardRepository
import me.sup2is.kotlinreactiveboard.domain.service.SequenceGenerator
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BoardService(
    val boardRepository: BoardRepository,
    val sequenceGenerator: SequenceGenerator
) {

    fun create(board: Board): Mono<Board> {
        return boardRepository.save(
            board.apply {
                id = sequenceGenerator.generateSequence(Board.SEQUENCE_NAME)
            }
        )
    }
}