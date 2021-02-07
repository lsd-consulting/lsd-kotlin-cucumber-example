package com.integreety.activityengine.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import javax.annotation.PostConstruct

@TestConfiguration
// TODO Doesn't seem to work
class PrettyPrintObjectMapperConfig(@Autowired val objectMapper: ObjectMapper) {

    @PostConstruct
    fun objectMapper() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    }
}