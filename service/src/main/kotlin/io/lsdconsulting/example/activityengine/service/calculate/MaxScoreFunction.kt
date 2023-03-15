package io.lsdconsulting.example.activityengine.service.calculate

import io.lsdconsulting.example.activityengine.dto.ActivityDto

fun ActivityDto.calculateMaxScore(): Int {
    return this.inputQuestions.sumOf { it.maxScore }
}
