package me.sup2is.kotlinreactiveboard.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Board")
class Board {

    @Id
    var id: Long = 0L
    var title: String = ""
    var contents: String = ""
    var author: String = ""
    var createAt: LocalDateTime = LocalDateTime.now()
    var updateAt: LocalDateTime = LocalDateTime.now()
}
