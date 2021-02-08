package com.integreety.activityengine.config

import com.googlecode.yatspec.cucumber.SpringBridge
import com.googlecode.yatspec.sequence.Participant
import com.googlecode.yatspec.sequence.Participants
import com.googlecode.yatspec.state.givenwhenthen.TestState
import com.nickmcdowall.lsd.http.common.DefaultHttpInteractionHandler
import com.nickmcdowall.lsd.http.common.HttpInteractionHandler
import com.nickmcdowall.lsd.http.naming.UserSuppliedDestinationMappings.userSuppliedDestinationMappings
import com.nickmcdowall.lsd.http.naming.UserSuppliedSourceMappings.userSuppliedSourceMappings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(SpringBridge::class)
@Configuration
class SequenceDiagramConfig {

    @Bean
    fun testState(): TestState {
        return TestState()
    }

    @Bean
    fun httpInterceptorHandlers(): List<HttpInteractionHandler> {
        return listOf(
                DefaultHttpInteractionHandler(
                        testState(),
                        userSuppliedSourceMappings(mapOf("/" to "ComponentTest")),
                        userSuppliedDestinationMappings(mapOf("/" to "App")))
        )
    }

    @Bean
    fun participants(): List<Participant> {
        return listOf(
                Participants.ACTOR.create("User"),
                Participants.PARTICIPANT.create("App", "Activity Engine")
        )
    }
}