package io.lsdconsulting.example.activityengine.api.response

import io.lsdconsulting.example.activityengine.api.shared.question.InputQuestion
import java.time.ZonedDateTime

data class ActivityResponse(
    val id: String,
    val lessonId: String,
    val inputQuestions: List<InputQuestion> = ArrayList(),
    val maxScore: Int?,
    val createdAt: ZonedDateTime
)
