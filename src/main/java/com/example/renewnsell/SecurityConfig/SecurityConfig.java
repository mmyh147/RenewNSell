package com.example.renewnsell.SecurityConfig;

import com.example.renewnsell.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final MyUserDetailsService myUserDetailsService;



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/user/login","/api/v1/user/logout", "/api/v1/customer/add", "/api/v1/employee/add").permitAll()
//                .requestMatchers("/api/v1/account/block/", "/api/v1/account/update").hasAuthority("EMPLOYEE")
//                .requestMatchers("/api/v1/account/block/", "/api/v1/employee/add", "/api/v1/account/update", "/api/v1/user/get-all", "/api/v1/user/delete").hasAuthority("ADMIN")
//                .requestMatchers("/api/v1/account/get-my-account/", "/api/v1/account/get-all-my-account", "/api/v1/account/deposit", "/api/v1/account/withdraw", "/api/v1/account/transfer", "/api/v1/account/active/", "/api/v1/account/delete/").hasAuthority("CUSTOMER")
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
