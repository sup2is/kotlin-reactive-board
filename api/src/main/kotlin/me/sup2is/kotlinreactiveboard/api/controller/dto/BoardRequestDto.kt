package me.sup2is.kotlinreactiveboard.api.controller.dto

import me.sup2is.kotlinreactiveboard.domain.model.Board

data class BoardRequestDto(
    val title: String,
    val contents: String,
    val author: String,
) {
    fun toModel(): Board = Board().apply {
        title = this@BoardRequestDto.title
        contents = this@BoardRequestDto.contents
        author = this@BoardRequestDto.author
    }
}
