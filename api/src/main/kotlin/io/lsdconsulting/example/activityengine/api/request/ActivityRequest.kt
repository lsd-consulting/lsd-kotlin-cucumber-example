package io.lsdconsulting.example.activityengine.api.request

import io.lsdconsulting.example.activityengine.api.shared.question.InputQuestion

data class ActivityRequest(
    val lessonId: String,
    val inputQuestions: List<InputQuestion> = ArrayList()
)
