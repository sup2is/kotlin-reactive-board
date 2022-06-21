package me.sup2is.kotlinreactiveboard.api.controller

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.api.service.BoardService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boards")
class BoardController(
    val boardService: BoardService
) {

    @PostMapping
    fun create(@RequestBody boardRequestDto: BoardRequestDto) = boardService.create(boardRequestDto)
}
