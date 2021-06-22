Feature: Calculate activitie's max score

  Scenario Outline: calculate the max possible score for an activity
    Given an existing activity with <number_of_questions> questions and with <points> each
    When the activity is requested
    Then the activity's max score is <max_score>
    Examples:
      | number_of_questions | points | max_score |
      | 1                   | 5      | 5         |
      | 2                   | 2      | 4         |
