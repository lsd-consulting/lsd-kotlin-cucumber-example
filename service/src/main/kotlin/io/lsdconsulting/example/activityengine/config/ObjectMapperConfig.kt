package io.lsdconsulting.example.activityengine.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.lsdconsulting.example.activityengine.config.objectmapper.ObjectMapperCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapperCreator().create()

    @Bean
    fun converter(objectMapper: ObjectMapper): MappingJackson2HttpMessageConverter? {
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = objectMapper
        return converter
    }
}
