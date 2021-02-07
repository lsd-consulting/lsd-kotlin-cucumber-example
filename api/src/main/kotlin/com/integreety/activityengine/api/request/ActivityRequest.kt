package com.integreety.activityengine.api.request

import com.integreety.activityengine.api.shared.question.InputQuestion
import java.util.*

data class ActivityRequest(
        val lessonId: String,
        val inputQuestions: List<InputQuestion> = ArrayList()
)