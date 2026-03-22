package com.hyunbindev.api.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev","local")
class SwaggerConfiguration {
    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("galaxy Archive api")
            .description("Galaxy Archive api")
            .version("1.0.0")


        return OpenAPI()
            .info(info)
    }
}