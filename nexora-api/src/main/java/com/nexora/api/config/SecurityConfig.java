

package com.nexora.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nexora.api.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuracao = new CorsConfiguration();

        configuracao.setAllowedOrigins(
            List.of("http://localhost:5173")
        );

        configuracao.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuracao.setAllowedHeaders(
            List.of("Authorization", "Content-Type")
        );

        configuracao.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource origem =
            new UrlBasedCorsConfigurationSource();

        origem.registerCorsConfiguration("/**", configuracao);

        return origem;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors ->
                cors.configurationSource(corsConfigurationSource())
            )

            .csrf(csrf -> csrf.disable())

            .formLogin(form -> form.disable())

            .httpBasic(basic -> basic.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/status", "/api/auth/**").permitAll()

                .requestMatchers("/api/usuarios", "/api/usuarios/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
