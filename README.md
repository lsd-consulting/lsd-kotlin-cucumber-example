# kotlin-cucumber-yatspec-lsd

This is a demo project to showcase the LSD library with Cucumber in Kotlin.

Using LSD/Yatspec and Cucumber it is easy to produce a diagram like this from tests:

![Diagram example](docs/diagram.png?raw=true)

Here is the Gherkin script used for the above diagram:

```gherkin
Feature: Find activity

  Scenario: find an activity by id
    Given an existing activity with lessonId ABC
    When the activity is requested by its id
    Then the activity with lessonId ABC is returned
```

# How to add LSD to a project with existing Cucumber/Gherkin scripts

1. Add the following test dependencies:
```groovy
componentTestImplementation("com.github.nickmcdowall:yatspec:2021.0.1")
componentTestImplementation("com.github.nickmcdowall:yatspec-lsd-interceptors:0.3.21")
```

2. Include the LSD Cucumber plugin, for example by setting the `cucumber.plugin` property in the `junit-platform.properties` file:
```bash
cucumber.plugin=com.googlecode.yatspec.cucumber.YatspecForCucumber:
```

**NOTE** Currently, Yatspec doesn't support parallel test execution, so do not change the value of the `cucumber.execution.parallel.enabled` property to true. Keep the default value which is `false`.

3. Import the following Spring config file in the tests:
```kotlin
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
```

The above is a standard Yatspec setup. Further details on it can be found [here](https://github.com/nickmcdowall/yatspec)

To import the above file add this annotation to the Spring based test:
```kotlin
@Import(SequenceDiagramConfig::class)
```

4. It's also helpful to generate the diagrams in a local folder rather than the default one. This can be done by setting the `yatspec.output.dir` property, for example like this:
```kotlin
tasks.withType<Test> {
    systemProperty("yatspec.output.dir", "$buildDir/reports/yatspec")
}
```