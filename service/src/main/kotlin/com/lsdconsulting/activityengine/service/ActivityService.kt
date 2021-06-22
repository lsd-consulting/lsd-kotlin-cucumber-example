package com.lsdconsulting.activityengine.service

import com.lsdconsulting.activityengine.config.logger.log
import com.lsdconsulting.activityengine.dto.ActivityDto
import com.lsdconsulting.activityengine.service.calculate.calculateMaxScore
import org.springframework.stereotype.Service
import java.time.ZonedDateTime.now
import java.util.UUID.randomUUID

@Service
class ActivityService {

    val activities: MutableMap<String, ActivityDto> = mutableMapOf()

    fun find(id: String): ActivityDto {
        log().info("Received request for activityId:{}", id)
        return activities[id]!!
    }

    fun findByLessonId(lessonId: String): Collection<ActivityDto> {
        log().info("Received request for lessonId:{}", lessonId)
        return activities.filter { it.value.lessonId.equals(lessonId, true) }.values
    }

    fun save(activityDto: ActivityDto): ActivityDto {
        log().info("Saving activity:{}", activityDto)
        val id = randomUUID().toString()
        val savedActivityDto = activityDto.copy(
            id = id,
            lessonId = activityDto.lessonId,
            createdAt = now(),
            maxScore = activityDto.calculateMaxScore(),
            inputQuestions = activityDto.inputQuestions
        )
        activities[id] = savedActivityDto
        return savedActivityDto
    }
}
