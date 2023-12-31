package com.stroganov.warehouse.configuration;


import com.stroganov.warehouse.domain.model.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/registration", "/images/**", "/registration-action", "/", "/hello", "/error").permitAll() //
                        .requestMatchers("/main/**").hasAnyRole(Role.ROLE_USER.getRoleName(), Role.ROLE_ADMIN.getRoleName(), Role.ROLE_ACCOUNTANT.getRoleName(), Role.ROLE_WAREHOUSE.getRoleName())
                        .requestMatchers("/call-user-form", "/call-user-management-form", "/users-management", "/user-disable-enable/**", "/user-delete/**","/statistics-form","/stat").hasRole(Role.ROLE_ADMIN.getRoleName())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/hello?logout=true")
                        .permitAll())
                .exceptionHandling(e->e.accessDeniedPage("/deniedpage"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
    /*
    http.csrf()
            .disable()
                .authorizeRequests()
                .antMatchers("/deleteEmployee/**")
                .hasRole("ADMIN")
                .antMatchers("/anonymous*")
                .anonymous()
                .antMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
     .loginPage("/login.html")
     .loginProcessingUrl("/perform_login")
     .defaultSuccessUrl("/homepage.html", true)
     .failureUrl("/login.html?error=true")
     .failureHandler(authenticationFailureHandler())
     .and()
     .logout()
     .logoutUrl("/perform_logout")
     .deleteCookies("JSESSIONID")
     .logoutSuccessHandler(logoutSuccessHandler());
    */
}
