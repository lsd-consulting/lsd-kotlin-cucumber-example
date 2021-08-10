package com.lsdconsulting.activityengine.step

import com.lsd.LsdContext
import com.lsdconsulting.activityengine.ActivityEngineApplication
import com.lsdconsulting.activityengine.api.request.ActivityRequest
import com.lsdconsulting.activityengine.api.response.ActivityResponse
import com.lsdconsulting.activityengine.client.ActivityEngineClient
import io.cucumber.java8.En
import io.cucumber.spring.CucumberContextConfiguration
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = [ActivityEngineApplication::class])
@EnableFeignClients(clients = [ActivityEngineClient::class])
@TestPropertySource("classpath:application-test.properties")
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
            LsdContext.getInstance().addFact("lessonId", lessonId)
        }

        Given("a non existent lessonId") {
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