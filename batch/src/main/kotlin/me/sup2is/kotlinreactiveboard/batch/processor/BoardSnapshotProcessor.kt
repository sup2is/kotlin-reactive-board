package me.sup2is.kotlinreactiveboard.batch.processor

import me.sup2is.kotlinreactiveboard.batch.model.BoardSnapshot
import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.batch.item.ItemProcessor

class BoardSnapshotProcessor : ItemProcessor<Board, BoardSnapshot> {

    override fun process(board: Board): BoardSnapshot? {
        return BoardSnapshot(board)
    }
}