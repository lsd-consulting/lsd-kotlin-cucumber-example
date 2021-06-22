package com.lsdconsulting.activityengine.rest.convert

import com.lsdconsulting.activityengine.api.request.ActivityRequest
import com.lsdconsulting.activityengine.api.response.ActivityResponse
import com.lsdconsulting.activityengine.dto.ActivityDto

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
        maxScore = activityDto.maxScore,
        createdAt = activityDto.createdAt!!
    )
}
