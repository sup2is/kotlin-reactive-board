package me.sup2is.kotlinreactiveboard.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.accept.RequestedContentTypeResolver

@Configuration
class WebConfig {

    @Bean
    fun requestWrapper(
        serverCodecConfigurer: ServerCodecConfigurer,
        requestedContentTypeResolver: RequestedContentTypeResolver
    ): ResponseWrapper {
        return ResponseWrapper(serverCodecConfigurer.writers, requestedContentTypeResolver)
    }
}
