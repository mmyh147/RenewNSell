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

//=====================================================CompanyController===========================
//=====================================================CustomerController===========================
                .requestMatchers("api/v1/customer/add").permitAll()
                .requestMatchers("api/v1/company/add").permitAll()
//=====================================================FixProductController===========================
                .requestMatchers("api/v1/fix-product/get-all-request-fix-product").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/fix-product/delete-fix/{fixProductId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/request-fix-product").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/accept-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/reject-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/get-fix-product/{fixProductId}").hasAnyAuthority("CUSTOMER","ADMIN","EMPLOYEE")
//=====================================================OrderCompanyController===========================
                .requestMatchers("api/v1/order-company/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order-company/get-all-order-by-company-id").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/update-order-company/{orderId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/delete-order-company/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order-company/change-status-of-order-company/{orderId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-all-total-price-orders-company").hasAnyAuthority("COMPANY","ADMIN")
                .requestMatchers("api/v1/order-company/get-all-orders-company-of-percent-of-defective/{precent}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-orders-company").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product-with-out-fix/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product-with-fix/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-number-of-order-for-one-product/{productId}").hasAuthority("COMPANY")
//=====================================================OrderController===========================
                .requestMatchers("api/v1/order/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/buy-order").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/update-order/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/delete-order/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/cancel-order/{orderId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/change-status-of-order/{orderId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/order/get-all-by-status/{status}").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/order/truck-order/{orderId}").hasAnyAuthority("EMPLOYEE","CUSTOMER")
                .requestMatchers("api/v1/order/get-all-customer-order").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                .requestMatchers("api/v1/order/get-all-order-by-product-id/{productId}").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/order/get-total-orders").hasAuthority("ADMIN")
//=====================================================ProductController===========================
//=====================================================ResponseFixProductController===========================
                .requestMatchers("api/v1/response-fix-product/get-all-response-request-fix-product").hasAuthority("ADMIN")
                .requestMatchers("api/v1/response-fix-product/response-request-fix-product/{fixProductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/response-fix-product/update-response-request-fix-product/{fixproductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/response-fix-product/delete-request-fix-product/{fixProductId}").hasAuthority("ADMIN")
//=====================================================ReviewController===========================
//=====================================================UserController===========================
//=====================================================WarrantyController===========================
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
