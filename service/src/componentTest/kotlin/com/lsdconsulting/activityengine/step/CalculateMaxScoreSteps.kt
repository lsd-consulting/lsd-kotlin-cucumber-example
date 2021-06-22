package com.lsdconsulting.activityengine.step

import com.lsdconsulting.activityengine.api.request.ActivityRequest
import com.lsdconsulting.activityengine.api.response.ActivityResponse
import com.lsdconsulting.activityengine.api.shared.question.InputQuestion
import com.lsdconsulting.activityengine.client.ActivityEngineClient
import io.cucumber.java8.En
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import java.util.*


class CalculateMaxScoreSteps(
    val activityEngineClient: ActivityEngineClient,
    private val testRestTemplate: TestRestTemplate,
) : En {

    private var activityResponse: ResponseEntity<ActivityResponse>? = null
    private var lessonId: String = UUID.randomUUID().toString()

    init {
        Given("an existing activity with {int} questions and with {int} each") { numberOfQuestions: Int, points: Int  ->
            val questions:MutableList<InputQuestion> = mutableListOf()
            for (i in 1..numberOfQuestions)
                questions.add(InputQuestion(id = i.toString(), displayOrder = i, question = "question", standardAnswer = "answer", maxScore = points))

            activityResponse = testRestTemplate.postForEntity(
                "/activities",
                ActivityRequest(lessonId = lessonId, inputQuestions = questions),
                ActivityResponse::class.java
            )
        }
        When("the activity is requested") {
            activityResponse = activityResponse?.body?.let { activityEngineClient.find(it.id) }
        }

        Then("the activity's max score is {int}") { expectedMaxScore: Int ->
            assertThat(activityResponse?.body?.maxScore, `is`(expectedMaxScore))
        }
    }
}