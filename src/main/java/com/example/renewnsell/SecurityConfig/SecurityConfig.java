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
                .requestMatchers("api/v1/order/change-status-of-order/{orderId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/response-fix-product/response-request-fix-product/{fixProductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/request-fix-product/accept-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")


                .requestMatchers("api/v1/product/add").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/delete/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/update/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/get-all").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/get-product/").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/get-product-category/","api/v1/product/get-product-title/","api/v1/product/get-product-defective/","api/v1/review/get-all-c").permitAll()
                .requestMatchers("api/v1/review/add").authenticated()
                .requestMatchers("api/v1/review/get/","api/v1/review/get-all-cu","api/v1/review/update/{reviewId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/review/delete/").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("api/v1/company/add").permitAll()
                .requestMatchers("api/v1/customer/add").permitAll()
                .requestMatchers("api/v1/order/buy-order").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/cancel-order/{orderId}").hasAnyAuthority("CUSTOMER","EMPLOYEE","ADMIN")
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
