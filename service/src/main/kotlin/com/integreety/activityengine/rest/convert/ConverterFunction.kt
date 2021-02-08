package com.integreety.activityengine.rest.convert

import com.integreety.activityengine.api.request.ActivityRequest
import com.integreety.activityengine.api.response.ActivityResponse
import com.integreety.activityengine.dto.ActivityDto

fun ActivityRequest.toActivityDto(): ActivityDto {
    return ActivityDto(
        lessonId = this.lessonId,
        inputQuestions = this.inputQuestions
    )
}

fun toActivityResponse(activityDto: ActivityDto): ActivityResponse {
    return ActivityResponse(
        id = activityDto.id!!,
        lessonId = activityDto.lessonId,
        inputQuestions = activityDto.inputQuestions,
        createdAt = activityDto.createdAt!!
    )
}
