package me.sup2is.kotlinreactiveboard.domain.service

import me.sup2is.kotlinreactiveboard.domain.model.DatabaseSequence
import mu.KotlinLogging
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class SequenceGenerator(
    val reactiveMongoOperations: ReactiveMongoOperations
) {

    private val logger = KotlinLogging.logger {}

    fun generateSequence(sequenceName: String): Long {
        return reactiveMongoOperations.findAndModify(
            Query(Criteria.where("_id").`is`(sequenceName)),
            Update().inc("seq", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            DatabaseSequence::class.java
        ).doOnSuccess { logger.info { "current seq = ${it.seq}" } }
            .toFuture()
            .get()
            .seq
    }
}
