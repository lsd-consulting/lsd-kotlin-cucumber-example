package io.lsdconsulting.example.activityengine.client

import io.lsdconsulting.example.activityengine.api.request.ActivityRequest
import io.lsdconsulting.example.activityengine.api.response.ActivityResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "activityEngineClient", url = "\${activityEngine.url}")
interface ActivityEngineClient {

    @GetMapping("/activities/{id}")
    fun find(@PathVariable("id") activityId: String): ResponseEntity<ActivityResponse>

    @GetMapping("/activities")
    fun findByLessonId(@RequestParam("lessonId") lessonId: String): ResponseEntity<List<ActivityResponse>>

    @PostMapping("/activities")
    fun save(activityRequest: ActivityRequest): ResponseEntity<ActivityResponse>
}
