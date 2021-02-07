package com.integreety.activityengine.service.calculate

import com.integreety.activityengine.dto.ActivityDto

fun ActivityDto.calculateMaxScore(): Int {
    return this.inputQuestions.sumBy { it.maxScore }
}
