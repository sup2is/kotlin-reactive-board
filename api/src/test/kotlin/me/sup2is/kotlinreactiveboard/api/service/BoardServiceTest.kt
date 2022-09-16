package me.sup2is.kotlinreactiveboard.api.service

import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardRequestDto
import me.sup2is.kotlinreactiveboard.api.controller.dto.BoardUpdateDto
import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.repository.BoardRepository
import me.sup2is.kotlinreactiveboard.domain.service.SequenceGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class BoardServiceTest {

    @InjectMocks
    lateinit var boardService: BoardService

    @Mock
    lateinit var boardRepository: BoardRepository

    @Mock
    lateinit var sequenceGenerator: SequenceGenerator

    @Test
    fun `게시판 생성`() {
        // given
        val boardRequestDto = BoardRequestDto(title = "", contents = "", author = "")

        given(boardRepository.save(any()))
            .willReturn(Mono.just(boardRequestDto.toModel()))

        given(sequenceGenerator.generateSequence(Board.SEQUENCE_NAME))
            .willReturn(0)

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

    @Test
    fun `게시판 수정`() {
        // given
        val origin = Board().apply {
            title = "origin"
            contents = "contents"
            updateAt = LocalDateTime.now()
        }

        val boardUpdateDto = BoardUpdateDto(title = "", contents = "")

        given(boardRepository.findById(anyLong()))
            .willReturn(Mono.just(origin))

        // when
        val updated = boardService.update(1L, boardUpdateDto)

        // then
        StepVerifier.create(updated)
            .assertNext {
                assertThat(it.title).isEqualTo(boardUpdateDto.title)
                assertThat(it.contents).isEqualTo(boardUpdateDto.contents)
                assertThat(it.updateAt).isBeforeOrEqualTo(origin.updateAt)
            }
            .verifyComplete()
    }
}
