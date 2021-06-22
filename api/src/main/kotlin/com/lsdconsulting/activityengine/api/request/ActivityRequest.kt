package com.lsdconsulting.activityengine.api.request

import com.lsdconsulting.activityengine.api.shared.question.InputQuestion
import java.util.*

data class ActivityRequest(
        val lessonId: String,
        val inputQuestions: List<InputQuestion> = ArrayList()
)