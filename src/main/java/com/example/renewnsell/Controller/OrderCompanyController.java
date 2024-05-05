package com.example.renewnsell.Controller;

import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.OrderCompanyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order-company")
public class OrderCompanyController {
private final OrderCompanyService orderCompanyService;
    Logger logger = LoggerFactory.getLogger(OrderCompanyController.class);
    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        logger.info("get-all");
        return ResponseEntity.status(200).body(orderCompanyService.getAll());
    }
    @GetMapping("/get-all-order-by-company-id")
    public ResponseEntity findAllByCompanyId(@AuthenticationPrincipal User user){
        logger.info("/get-all-order-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.findAllByCompanyId(user.getId()));
    }

    //===============================

    //===========================

    //getTodayProfitForCompany

    @GetMapping("/get-all-total-price-orders-company")
    public ResponseEntity getTodayProfitForCompany(@AuthenticationPrincipal User user){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTodayProfitForCompany(user.getId()));
    }
}



