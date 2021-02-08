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