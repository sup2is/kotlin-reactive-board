package me.sup2is.kotlinreactiveboard.domain.model

import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("BoardSnapshot")
class BoardSnapshot(
    val title: String,
    val contents: String,
    val author: String,
    val publishedMonth: String,
    var createAt: LocalDateTime = LocalDateTime.now(),
    var updateAt: LocalDateTime = LocalDateTime.now()
) {

    @Id
    val id: String = ""
    constructor(board: Board) : this(board.title, board.contents, board.author, "${board.createAt.month}")

}
