package io.lsdconsulting.example.activityengine.config.feign

import feign.codec.Decoder
import feign.codec.Encoder
import feign.optionals.OptionalDecoder
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.http.converter.autoconfigure.ClientHttpMessageConvertersCustomizer
import org.springframework.cloud.openfeign.support.FeignHttpMessageConverters
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Bean
    fun feignHttpMessageConverters(
        clientCustomizers: ObjectProvider<ClientHttpMessageConvertersCustomizer>,
        feignCustomizers: ObjectProvider<HttpMessageConverterCustomizer>,
    ) = FeignHttpMessageConverters(clientCustomizers, feignCustomizers)

    @Bean
    fun feignEncoder(converters: ObjectProvider<FeignHttpMessageConverters>): Encoder =
        SpringEncoder(converters)

    @Bean
    fun feignDecoder(converters: ObjectProvider<FeignHttpMessageConverters>): Decoder =
        OptionalDecoder(ResponseEntityDecoder(SpringDecoder(converters)))
}
