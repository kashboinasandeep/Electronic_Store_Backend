
package com.electronicstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.electronicstore.security.JwtAuthenticationEntryPoint;
import com.electronicstore.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    public static final String[] PUBLIC_URLS = {
        "/api/v1/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setMaxAge(4000L);
                return corsConfiguration;
            }
        }));

        security.csrf(csrf -> csrf.disable());

        security.authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC_URLS).permitAll()
            .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
            .requestMatchers("/products/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
            .requestMatchers("/categories/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST,"/auth/generate-token","/auth/login-with-google").permitAll()
            .requestMatchers("/auth/**").authenticated()
            .anyRequest().permitAll()
        );

        security.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));
        security.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
