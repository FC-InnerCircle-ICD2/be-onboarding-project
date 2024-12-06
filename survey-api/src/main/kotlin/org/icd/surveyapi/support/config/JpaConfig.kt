package org.icd.surveyapi.support.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("org.icd.surveycore")
@EnableJpaRepositories("org.icd.surveycore")
class JpaConfig {
}