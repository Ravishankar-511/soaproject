package soa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout.disable())

            .authorizeHttpRequests(auth -> auth

                // Login is publicly accessible
                .requestMatchers("/auth/**").permitAll()

                // ADMIN and FACULTY can access student information
                .requestMatchers("/students/**")
                    .hasAnyRole("ADMIN", "FACULTY")

                // ADMIN and FACULTY can access attendance
                .requestMatchers("/attendance/**")
                    .hasAnyRole("ADMIN", "FACULTY")

                // ADMIN and FACULTY can access results
                .requestMatchers("/results/**")
                    .hasAnyRole("ADMIN", "FACULTY")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}