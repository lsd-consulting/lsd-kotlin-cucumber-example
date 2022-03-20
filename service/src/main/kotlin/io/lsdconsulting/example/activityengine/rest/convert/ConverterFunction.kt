package io.lsdconsulting.example.activityengine.rest.convert

import io.lsdconsulting.example.activityengine.api.request.ActivityRequest
import io.lsdconsulting.example.activityengine.api.response.ActivityResponse
import io.lsdconsulting.example.activityengine.dto.ActivityDto

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
