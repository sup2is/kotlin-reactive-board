package me.sup2is.kotlinreactiveboard.api.service

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardUpdateDto
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

    fun create(boardRequestDto: BoardRequestDto): Mono<Board> {
        return boardRepository.save(
            boardRequestDto.toModel().apply {
                id = sequenceGenerator.generateSequence(Board.SEQUENCE_NAME)
            }
        )
    }

    fun update(boardId: Long, boardUpdateDto: BoardUpdateDto): Mono<Board> {
        return get(boardId)
            .switchIfEmpty(
                Mono.error(IllegalArgumentException())
            )
            .map {
                it.update(boardUpdateDto.toModel())
            }.doOnNext {
                boardRepository.save(it)
            }
    }

    fun get(boardId: Long) = boardRepository.findById(boardId)

    fun getAll() = boardRepository.findAll()
}
