package com.caiolima.bcontrol.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
public class SecurityJPAQueryConfiguration {

    @Bean
    public SecurityEvaluationContextExtension securityExtension() {
        return new SecurityEvaluationContextExtension();
    }

}
