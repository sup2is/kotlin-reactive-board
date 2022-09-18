package me.sup2is.kotlinreactiveboard.api.config

import me.sup2is.kotlinreactiveboard.api.controller.model.ApiResponse
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.web.reactive.HandlerResult
import org.springframework.web.reactive.accept.RequestedContentTypeResolver
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class ResponseWrapper(
    writers: MutableList<HttpMessageWriter<*>>,
    resolver: RequestedContentTypeResolver
) : ResponseBodyResultHandler(writers, resolver) {

    override fun supports(result: HandlerResult): Boolean {
        val isSupportTypes = result.returnType.resolve() == Mono::class.java ||
            result.returnType.resolve() == Flux::class.java
        val isAlreadyResponse = result.returnType.generics[0] == ApiResponse::class
        return isSupportTypes && !isAlreadyResponse
    }

    override fun handleResult(exchange: ServerWebExchange, result: HandlerResult): Mono<Void> {

        val body = when (val value = result.returnValue) {
            is Mono<*> -> value
            is Flux<*> -> value.collectList()
            else -> throw ClassCastException("The \"body\" should be Mono<*> or Flux<*>!")
        }.map {
            ApiResponse.success(it)
        }.onErrorResume {
            ApiResponse.failed(it.message).toMono()
        }

        val returnTypeSource = result.returnTypeSource

        return writeBody(body, returnTypeSource, exchange)
    }
}
