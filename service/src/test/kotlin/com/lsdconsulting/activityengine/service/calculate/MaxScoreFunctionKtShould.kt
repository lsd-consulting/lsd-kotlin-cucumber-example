package com.lsdconsulting.activityengine.service.calculate

import com.lsdconsulting.activityengine.api.shared.question.InputQuestion
import com.lsdconsulting.activityengine.dto.ActivityDto
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
