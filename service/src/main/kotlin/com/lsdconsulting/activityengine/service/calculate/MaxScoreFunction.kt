package com.lsdconsulting.activityengine.service.calculate

import com.lsdconsulting.activityengine.dto.ActivityDto

fun ActivityDto.calculateMaxScore(): Int {
    return this.inputQuestions.sumBy { it.maxScore }
}
