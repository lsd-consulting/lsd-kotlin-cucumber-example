package io.lsdconsulting.example.activityengine.dto

import io.lsdconsulting.example.activityengine.api.shared.question.InputQuestion
import java.time.ZonedDateTime

data class ActivityDto(
    val id: String? = null,
    val lessonId: String,
    val maxScore: Int? = null,
    val inputQuestions: List<InputQuestion> = ArrayList(),
    val createdAt: ZonedDateTime? = null
)
