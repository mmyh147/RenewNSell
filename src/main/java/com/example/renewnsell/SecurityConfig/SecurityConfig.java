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
                .requestMatchers("api/v1/customer/add").permitAll()
               .requestMatchers("api/v1/auth/add-employee").permitAll()
                .requestMatchers("api/v1/request-fix-product/request-fix-product").authenticated()
                .requestMatchers("api/v1/request-fix-product/change-status-of-fix-product/{fixProductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/request-fix-product/response-request-fix-product/{fixProductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/request-fix-product/accept-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/buy-order").hasAuthority("CUSTOMER")

                .requestMatchers("api/v1/product/add").hasAuthority("COMPANY")
//
//
//                .requestMatchers("api/v1/request-fix-product/request-fix-product").authenticated()
//                .requestMatchers("api/v1/request-fix-product/request-fix-product").authenticated()
//                .requestMatchers("api/v1/request-fix-product/request-fix-product").authenticated()
//                .requestMatchers("api/v1/request-fix-product/request-fix-product").authenticated()
//
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
