package me.sup2is.kotlinreactiveboard.api.controller

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.api.controller.model.ApiResponse
import me.sup2is.kotlinreactiveboard.api.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    fun create(@RequestBody boardRequestDto: BoardRequestDto) =
        boardService.create(boardRequestDto).map { ApiResponse(it) }

    @GetMapping("/{boardId}")
    fun getOne(@PathVariable("boardId") boardId: Long) = boardService.get(boardId).map { ApiResponse(it) }

    @GetMapping
    fun getAll() = boardService.getAll().map { ApiResponse(it) }
}
