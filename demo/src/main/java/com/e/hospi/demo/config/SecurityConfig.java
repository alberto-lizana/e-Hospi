package com.e.hospi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                                .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR")
                                .requestMatchers("/recepcionista/**").hasRole("RECEPCIONISTA")
                                .requestMatchers("/api/recepcionista/**").hasRole("RECEPCIONISTA")
                                .requestMatchers("/doctor/**").hasRole("MEDICO")
                                .requestMatchers("/api/doctor/**").hasRole("MEDICO")
                                .requestMatchers("/Styles/**", "/Js/**", "/Img/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(customAuthenticationSuccessHandler())
                                .failureUrl("/login?error")
                                .permitAll()
                )

                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); 
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider())
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var roles = authentication.getAuthorities().toString();
            
            System.out.println("[SUCCESS HANDLER] Usuario autenticado: " + authentication.getName());
            System.out.println("[SUCCESS HANDLER] Roles asignados: " + roles);
    
            if (roles.contains("ROLE_ADMINISTRADOR")) {
                System.out.println("[SUCCESS HANDLER] Redirigiendo a /admin");
                response.sendRedirect("/admin");
            } else if (roles.contains("ROLE_RECEPCIONISTA")) {
                System.out.println("[SUCCESS HANDLER] Redirigiendo a /recepcionista");
                response.sendRedirect("/recepcionista");
            } else if (roles.contains("ROLE_MEDICO")) {
                System.out.println("[SUCCESS HANDLER] Redirigiendo a /doctor");
                response.sendRedirect("/doctor");
            } else {
                System.out.println("[SUCCESS HANDLER] No se encontró un rol válido, redirigiendo a login error");
                response.sendRedirect("/login?error=sin-rol");
            }
        };
    }
    
}
