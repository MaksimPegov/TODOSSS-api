package com.maksimpegov.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
                // will be realized in the future
                .permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();

    }

}
