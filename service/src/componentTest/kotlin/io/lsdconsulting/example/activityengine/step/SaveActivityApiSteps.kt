package io.lsdconsulting.example.activityengine.step

import com.lsd.LsdContext
import io.cucumber.java8.En
import io.lsdconsulting.example.activityengine.api.request.ActivityRequest
import io.lsdconsulting.example.activityengine.api.response.ActivityResponse
import io.lsdconsulting.example.activityengine.api.shared.question.InputQuestion
import io.lsdconsulting.example.activityengine.client.ActivityEngineClient
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class SaveActivityApiSteps(
    val activityEngineClient: ActivityEngineClient,
    val testRestTemplate: TestRestTemplate,
) : En {

    lateinit var activityRequest: ActivityRequest
    lateinit var activityResponse: ResponseEntity<ActivityResponse>

    private var lessonId = UUID.randomUUID().toString()

    init {

        // TODO Is there a better way to do it? (This is to exclude the test calls from LSD)
        testRestTemplate.restTemplate.interceptors.clear()

        Given("a request to save an empty activity") {
            LsdContext.getInstance().addFact("lessonId", lessonId)

            activityRequest = ActivityRequest(lessonId = lessonId)
        }

        When("the request is received") {
            activityResponse = activityEngineClient.save(activityRequest)

            LsdContext.getInstance().addFact("activity id", activityResponse.body?.id)
        }

        Then("the request is accepted") {
            assertThat(activityResponse.statusCode, equalTo(HttpStatus.ACCEPTED))
        }

        And("the activity is saved") {
            activityResponse = retrieveActivityById()
            assertThat(this.activityResponse.body?.lessonId, equalTo(lessonId))
            assertThat(activityResponse.body?.lessonId, equalTo(lessonId))
        }

        Given("a request to save an activity with a question") {
            LsdContext.getInstance().addFact("lessonId", lessonId)

            val inputQuestion = InputQuestion(
                id = UUID.randomUUID().toString(),
                displayOrder = 1,
                question = "question",
                standardAnswer = "standardAnswer",
                maxScore = 10
            )
            activityRequest = ActivityRequest(lessonId = lessonId, inputQuestions = listOf(inputQuestion))
        }

        And("it is saved with the question") {
            assertThat(this.activityResponse.body?.inputQuestions, hasSize(1))
            assertThat(this.activityResponse.body!!.inputQuestions[0], hasProperty("question", equalTo("question")))
        }
    }

    private fun retrieveActivityById(): ResponseEntity<ActivityResponse> {
        val activityId = activityResponse.body?.id
        return testRestTemplate.getForEntity("/activities/$activityId", ActivityResponse::class.java)
    }
}
