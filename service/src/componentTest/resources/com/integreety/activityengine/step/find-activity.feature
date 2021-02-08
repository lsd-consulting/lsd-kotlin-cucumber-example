Feature: Find activity

  Scenario: find an activity by id
    Given an existing activity with lessonId ABC
    When the activity is requested by its id
    Then the activity with lessonId ABC is returned

  Scenario: find an activity by lessonId
    Given an existing activity with lessonId XYZ
    When the activity is requested by lessonId XYZ
    Then the activity with lessonId XYZ is returned

  Scenario: return empty list when there is no activity with the requested lessonId
    Given a non existent lessonId
    When the activity is requested by lessonId ZYX
    Then empty list is returned
