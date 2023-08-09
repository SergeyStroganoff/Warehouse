package com.stroganov.warehouse.configuration;


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
public class WebSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

/*
    @Bean
    public UserDetailsService userDetailsService() {
        //  UserDetails user1 = User.withUsername("user1")
        //          .password(passwordEncoder().encode("123"))
        //          .roles("USER")
        //          .build();
        //  UserDetails user2 = User.withUsername("user2")
        //          .password(passwordEncoder().encode("123"))
        //          .roles("USER")
        //          .build();
        //  UserDetails admin = User.withUsername("admin")
        //          .password(passwordEncoder().encode("123"))
        //          .roles("ADMIN")
        //          .build();
        //  return new InMemoryUserDetailsManager(user1, user2, admin);
        return new JdbcUserDetailsManager(dataSource);
    }

 */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/hello").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
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
