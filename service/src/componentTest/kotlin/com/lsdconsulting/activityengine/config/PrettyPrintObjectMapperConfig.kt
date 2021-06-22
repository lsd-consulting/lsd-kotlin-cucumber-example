package com.lsdconsulting.activityengine.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import javax.annotation.PostConstruct

@TestConfiguration
class PrettyPrintObjectMapperConfig(@Autowired val objectMapper: ObjectMapper) {

    @PostConstruct
    fun objectMapper() {
        // Temporary - until it is automated in LSD
        // TODO Remove after adding LSD Interceptors
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    }
}