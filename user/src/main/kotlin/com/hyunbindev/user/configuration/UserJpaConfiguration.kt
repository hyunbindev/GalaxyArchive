package com.hyunbindev.user.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.hyunbindev.user.entity"])
@EnableJpaRepositories(basePackages = ["com.hyunbindev.user.repository"])
class UserJpaConfiguration {
}