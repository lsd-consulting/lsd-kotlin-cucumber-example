Feature: Save activity

  Scenario: save an empty activity
    Given a request to save an empty activity
    When the request is received
    Then the request is accepted
    And the activity is saved

  Scenario: save an activity with a single question
    Given a request to save an activity with a question
    When the request is received
    Then the request is accepted
    And the activity is saved
    And it is saved with the question
