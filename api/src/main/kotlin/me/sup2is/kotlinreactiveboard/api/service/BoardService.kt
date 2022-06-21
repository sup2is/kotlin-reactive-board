package me.sup2is.kotlinreactiveboard.api.service

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.domain.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    val boardRepository: BoardRepository
) {

    fun create(boardRequestDto: BoardRequestDto) = boardRepository.save(boardRequestDto.toModel())
}
