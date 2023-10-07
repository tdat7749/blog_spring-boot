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

                        //socket
                        .requestMatchers(HttpMethod.GET,"/ws/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/user/**").permitAll()

                        //auth
                        .requestMatchers("/api/auth/**").permitAll()

                        //upload file
                        .requestMatchers("/api/filestorage/**").hasAnyRole("USER","ADMIN")

                        //series
                        .requestMatchers(HttpMethod.GET,"/api/series/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/series/").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/series/{seriesId}").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/series/{id}").hasAnyRole("USER","ADMIN")

                        //tag
                        .requestMatchers(HttpMethod.GET,"/api/tags/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/tags/").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/tags/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/tags/{id}").hasAnyRole("ADMIN")

                        //user
                        .requestMatchers(HttpMethod.GET,"/api/users/top10-noti").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/notifications").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users/me").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/users/permission").hasAnyRole("ADMIN")

                        //follow
                        .requestMatchers(HttpMethod.POST,"/api/follows/{id}").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/follows/{id}").hasAnyRole("USER","ADMIN")

                        //post
                        .requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/posts/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/posts/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/posts/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/posts/**").hasAnyRole("USER","ADMIN")

                        //comment
                        .requestMatchers(HttpMethod.GET,"/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/comments/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/comments/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/comments/**").hasAnyRole("USER","ADMIN")

                        .anyRequest().authenticated())
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
