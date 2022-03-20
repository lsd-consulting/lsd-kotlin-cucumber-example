package io.lsdconsulting.example.activityengine.service.calculate

import io.lsdconsulting.example.activityengine.api.shared.question.InputQuestion
import io.lsdconsulting.example.activityengine.dto.ActivityDto
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class MaxScoreFunctionKtShould {

    @Test
    fun calculateMaxScore() {
        val inputQuestion = InputQuestion(id = "id", displayOrder = 1, question = "question", standardAnswer = "answer", maxScore = 5)
        val underTest = ActivityDto(lessonId = "id", inputQuestions = listOf(inputQuestion, inputQuestion))

        val result = underTest.calculateMaxScore()

        assertThat(result, equalTo(10))
    }
}
