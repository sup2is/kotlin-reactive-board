package me.sup2is.kotlinreactiveboard.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SequenceGeneratorTest {

    @Autowired
    lateinit var sequenceGenerator: SequenceGenerator

    @Test
    fun `sequenceGenerator 테스트`() {

        // given
        val sequenceName = "test_sequence"
        val beforeSeq = sequenceGenerator.generateSequence(sequenceName)

        // when
        val nextSeq = sequenceGenerator.generateSequence(sequenceName)

        // then
        assertThat(beforeSeq).isEqualTo(nextSeq - 1)
    }
}
