package io.lsdconsulting.example.activityengine.step

import com.lsd.core.LsdContext
import com.lsd.core.builders.MessageBuilder.Companion.messageBuilder
import io.cucumber.java8.En
import io.lsdconsulting.example.activityengine.api.request.ActivityRequest
import io.lsdconsulting.example.activityengine.api.response.ActivityResponse
import io.lsdconsulting.example.activityengine.client.ActivityEngineClient
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.http.ResponseEntity

class FindActivityApiSteps(
    val activityEngineClient: ActivityEngineClient,
    private val testRestTemplate: TestRestTemplate,
) : En {

    private var activityResponse: ResponseEntity<ActivityResponse>? = null
    private lateinit var activityResponses: ResponseEntity<List<ActivityResponse>>

    init {

        testRestTemplate.restTemplate.interceptors.clear()

        Given("an existing activity with lessonId {word}") { lessonId: String ->
            activityResponse = testRestTemplate.postForEntity(
                "/activities",
                ActivityRequest(lessonId = lessonId),
                ActivityResponse::class.java
            )
            LsdContext.instance.addFact("lessonId", lessonId)
        }

        Given("a non existent lessonId") {
            LsdContext.instance.capture(messageBuilder().from("A").to("B").label("message").data("hello").build())
        }

        When("the activity is requested by its id") {
            activityResponse = activityResponse?.body?.let { activityEngineClient.find(it.id) }
        }

        When("the activity is requested by lessonId {word}") { lessonId: String ->
            activityResponses = activityEngineClient.findByLessonId(lessonId)
        }

        Then("the activity with lessonId {word} is returned") { lessonId: String ->
            assertThat(activityResponse?.body?.lessonId, equalTo(lessonId))
        }

        Then("empty list is returned") {
            assertThat(activityResponses.body, `is`(empty()))
        }
    }
}
