package me.sup2is.kotlinreactiveboard.api.controller

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardUpdateDto
import me.sup2is.kotlinreactiveboard.api.service.BoardService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/boards")
class BoardController(
    val boardService: BoardService
) {

    @PostMapping
    fun create(@RequestBody boardRequestDto: BoardRequestDto) =
        boardService.create(boardRequestDto)

    @PutMapping("/{boardId}")
    fun update(
        @RequestBody boardUpdateDto: BoardUpdateDto,
        @PathVariable("boardId") boardId: Long
    ) =
        boardService.update(boardId, boardUpdateDto)

    @GetMapping("/{boardId}")
    fun getOne(@PathVariable("boardId") boardId: Long) =
        boardService.get(boardId)

    @GetMapping
    fun getAll() = boardService.getAll()
}
