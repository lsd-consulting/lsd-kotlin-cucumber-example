Feature: Find activity

  Scenario: find an activity by id
  Given an existing activity with lessonId ABC
  When the activity is requested by the returned id
  Then the activity is returned with lessonId ABC

  Scenario: find an activity by lessonId
  Given an existing activity with lessonId XYZ
  When the activity is requested by lessonId XYZ
  Then the activity is returned with lessonId XYZ

  Scenario: return empty list when there is no activity with the requested sowId
  Given a non existent lessonId
  When the activity is requested by lessonId ZYX
  Then empty list is returned
