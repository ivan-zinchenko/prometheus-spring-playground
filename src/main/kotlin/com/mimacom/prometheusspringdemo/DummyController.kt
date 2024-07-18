package com.mimacom.prometheusspringdemo

import io.micrometer.core.annotation.Timed
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@Configuration
class TimedAspectConfiguration {
    @Bean
    fun timedAspect(registry: MeterRegistry) = TimedAspect(registry)
}

@RestController
class DummyController {
    @GetMapping("/dummy/{statusCode}")
    @Timed(value="my_dummy_method_metric", description = "My custom timed metric for dummy controller requests")
    fun dummy(@PathVariable statusCode: Int): ResponseEntity<String> {
        HttpStatus.resolve(statusCode)?.let {
            return ResponseEntity.status(statusCode).body(it.reasonPhrase)
        }
        return ResponseEntity.internalServerError().body("Unknown")
    }
}