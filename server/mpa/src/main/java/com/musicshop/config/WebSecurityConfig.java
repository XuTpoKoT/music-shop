package com.musicshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final String apiPrefix;
    private final String[] publicUrls;

    public WebSecurityConfig(@Value("${api-version}") String apiVersion) {
        this.apiPrefix = "/" + apiVersion;
        publicUrls = Stream.of("/products", "/products/*").map(s ->(apiPrefix + s)).toList()
                .toArray(new String[0]);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, publicUrls)
                            .permitAll()
                        .requestMatchers(HttpMethod.GET, "/styles/*")
                            .permitAll()
                        .requestMatchers(apiPrefix + "/auth/sign-up")
                            .anonymous()
                        .anyRequest()
                            .authenticated()
                )
                .formLogin(formLogin -> formLogin
                    .loginPage(apiPrefix + "/auth/sign-in").permitAll()
                    .defaultSuccessUrl(apiPrefix + "/products")
                )
                .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher(apiPrefix + "/auth/sign-out", "POST"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl(apiPrefix + "/products")
                );

        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
