package me.sup2is.kotlinreactiveboard.api.config

import me.sup2is.kotlinreactiveboard.api.controller.BoardController
import me.sup2is.kotlinreactiveboard.api.controller.model.ApiResponse
import me.sup2is.kotlinreactiveboard.api.service.BoardService
import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

@WebFluxTest(BoardController::class)
@Import(WebConfig::class)
class ResponseWrapperTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var boardService: BoardService

    @Test
    fun `API가 성공하면 ApiResponse에 wrapping한다`() {

        // given
        given(boardService.get(anyLong()))
            .willReturn(Board().toMono())

        // when & then
        webTestClient.get()
            .uri("/boards/{boardId}", 1)
            .exchange()
            .expectStatus().isOk
            .expectBody(ApiResponse::class.java)
            .consumeWith {
                assertThat(it.responseBody!!.result).isTrue
                assertThat(it.responseBody!!.data).isNotNull
            }
    }

    @Test
    fun `API가 성공하면 ApiResponse에 wrapping한다2`() {

        // given
        given(boardService.getAll())
            .willReturn(Flux.just(Board(), Board()))

        // when & then
        webTestClient.get()
            .uri("/boards")
            .exchange()
            .expectStatus().isOk
            .expectBody(ApiResponse::class.java)
            .consumeWith {
                assertThat(it.responseBody!!.result).isTrue
                assertThat(it.responseBody!!.data).isNotNull
            }
    }
}