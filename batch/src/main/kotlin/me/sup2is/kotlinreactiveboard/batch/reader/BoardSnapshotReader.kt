package me.sup2is.kotlinreactiveboard.batch.reader

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.repository.BoardRepository
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

@Component
class BoardSnapshotReader(
    val boardRepository: BoardRepository
) : ItemReader<Board> {

    override fun read(): Board? {
        return null
    }
}