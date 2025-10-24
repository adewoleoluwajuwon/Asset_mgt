package com.Jguides.spingboot.config;

import com.Jguides.spingboot.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // No @Bean userDetailsService(...) here — let Spring pick up your @Service JpaUserDetailsService

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            JpaUserDetailsService jpaUserDetailsService,
            PasswordEncoder encoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jpaUserDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers(
                                "/manifest.json",
                                "/service-worker.js",
                                "/icons/**",
                                "/offline.html",
                                "/favicon.ico",
                                "/apple-touch-icon.png"
                        ).permitAll()
                        .requestMatchers("/login", "/error").permitAll()
                        .requestMatchers("/", "/dashboard").authenticated()
                        .requestMatchers("/users/**").hasRole("ADMIN_MANAGER")
                        .requestMatchers("/warehouses/**", "/assets/**")
                        .hasAnyRole("ADMIN_MANAGER", "STORE_MANAGER")
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
