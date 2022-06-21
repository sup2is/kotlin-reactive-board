package me.sup2is.kotlinreactiveboard.api.service

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.domain.repository.BoardRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class BoardServiceTest {

    @InjectMocks
    lateinit var boardService: BoardService

    @Mock
    lateinit var boardRepository: BoardRepository

    @Test
    fun `게시판 생성`() {
        // given
        val boardRequestDto = BoardRequestDto(title = "", contents = "", author = "")

        given(boardRepository.save(any()))
            .willReturn(Mono.just(boardRequestDto.toModel()))

        // when
        val create = boardService.create(boardRequestDto)

        // then
        StepVerifier.create(create)
            .expectNextMatches {
                it.title == boardRequestDto.title &&
                    it.contents == boardRequestDto.contents &&
                    it.author == boardRequestDto.author
            }
            .expectComplete()
            .verify()
    }
}
