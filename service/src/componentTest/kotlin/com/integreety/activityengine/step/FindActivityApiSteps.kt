package com.integreety.activityengine.step

import com.googlecode.yatspec.state.givenwhenthen.TestState
import com.integreety.activityengine.ActivityEngineApplication
import com.integreety.activityengine.api.request.ActivityRequest
import com.integreety.activityengine.api.response.ActivityResponse
import com.integreety.activityengine.client.ActivityEngineClient
import com.integreety.activityengine.config.PrettyPrintObjectMapperConfig
import com.integreety.activityengine.config.SequenceDiagramConfig
import io.cucumber.java8.En
import io.cucumber.spring.CucumberContextConfiguration
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = [ActivityEngineApplication::class])
@EnableFeignClients(clients = [ActivityEngineClient::class])
@TestPropertySource("classpath:application-test.properties")
@Import(SequenceDiagramConfig::class, PrettyPrintObjectMapperConfig::class)
class FindActivityApiSteps(
    val activityEngineClient: ActivityEngineClient,
    private val testRestTemplate: TestRestTemplate,
    val testState: TestState
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
            testState.interestingGivens().add("lessonId", lessonId)
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