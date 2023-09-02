package com.example.blog_springboot.configs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtFilter jwtFilter,AuthenticationProvider authenticationProvider){
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        logger.info("Security run");
        http
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        //auth
                        .requestMatchers("/api/auth/**").permitAll()

                        //series
                        .requestMatchers(HttpMethod.GET,"/api/series/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/series/").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/series/{seriesId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/series/{id}").hasAnyRole("ADMIN")

                        .anyRequest().authenticated())
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
