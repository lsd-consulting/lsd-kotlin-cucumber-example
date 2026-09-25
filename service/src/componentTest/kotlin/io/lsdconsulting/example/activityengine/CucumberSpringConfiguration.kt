package io.lsdconsulting.example.activityengine

import io.cucumber.spring.CucumberContextConfiguration
import io.lsdconsulting.example.activityengine.client.ActivityEngineClient
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.TestPropertySource

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = [ActivityEngineApplication::class])
@AutoConfigureTestRestTemplate
@EnableFeignClients(clients = [ActivityEngineClient::class])
@TestPropertySource("classpath:application-test.properties")
class CucumberSpringConfiguration
