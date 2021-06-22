package com.lsdconsulting.activityengine.rest

import com.lsdconsulting.activityengine.api.request.ActivityRequest
import com.lsdconsulting.activityengine.api.response.ActivityResponse
import com.lsdconsulting.activityengine.config.logger.log
import com.lsdconsulting.activityengine.rest.convert.toActivityDto
import com.lsdconsulting.activityengine.rest.convert.toActivityResponse
import com.lsdconsulting.activityengine.service.ActivityService
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.web.bind.annotation.*

@RestController
class ActivityRestController(val activityService: ActivityService) {

    @GetMapping("/activities/{id}")
    fun find(@PathVariable id: String): ActivityResponse? {
        log().info("Received find request for activityId={}", id)
        return toActivityResponse(activityService.find(id))
    }

    @GetMapping("/activities")
    fun findByLessonId(@RequestParam lessonId: String): List<ActivityResponse> {
        log().info("Received find request for lessonId={}", lessonId)
        return activityService.findByLessonId(lessonId).map { toActivityResponse(it) }
    }

    @PostMapping("/activities")
    @ResponseStatus(ACCEPTED)
    fun save(@RequestBody activityRequest: ActivityRequest): ActivityResponse {
        return toActivityResponse(activityService.save(activityRequest.toActivityDto()))
    }
}