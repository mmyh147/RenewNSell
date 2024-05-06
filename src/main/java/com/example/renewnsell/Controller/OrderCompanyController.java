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

    @GetMapping("/company-total-profit")
    public ResponseEntity getTotalProfitForCompany(@AuthenticationPrincipal User user){
        logger.info("Request total profit for the company by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForCompany(user.getId()));
    }

    @GetMapping("/company-today-profit")
    public ResponseEntity getTodayProfitForCompany(@AuthenticationPrincipal User user){
        logger.info("get today profit for the company by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTodayProfitForCompany(user.getId()));
    }

    @GetMapping("/company-current-month-profit")
    public ResponseEntity getCurrentMonthProfitForCompany(@AuthenticationPrincipal User user){
        logger.info("get current month profit by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getCurrentMonthProfitForCompany(user.getId()));
    }

    @GetMapping("/company-all-sold-product")
    public ResponseEntity getAllSoldProductForCompany(@AuthenticationPrincipal User user){
        logger.info("retrieve all sold product by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProductsSoldForCompany(user.getId()));
    }

    @GetMapping("/company-today-sold-product")
    public ResponseEntity getTodaySoldProductForCompany(@AuthenticationPrincipal User user){
        logger.info("retrieve today sold product by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.countProductsSoldTodayForCompany(user.getId()));
    }

    @GetMapping("/company-current-month-sold-product")
    public ResponseEntity getCurrentMonthSoldProductForCompany(@AuthenticationPrincipal User user){
        logger.info("retrieve current month sold product by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.countProductsSoldCurrentMonthForCompany(user.getId()));
    }

//    @GetMapping("/company-last-month-sold-product")
//    public ResponseEntity getLastMonthSoldProductForCompany(@AuthenticationPrincipal User user){
//        logger.info("retrieve current month sold product by : " + user);
//        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.countProductsSoldLastMonthForCompany(user.getId()));
//    }


    @GetMapping("/company-last-month-profit")
    public ResponseEntity getLastMonthProfitForCompany(@AuthenticationPrincipal User user){
        logger.info("get last month profit by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getLastMonthProfitForCompany(user.getId()));
    }


    @GetMapping("/all-total-profit")
    public ResponseEntity getTotalProfitForAll(@AuthenticationPrincipal User user){
        logger.info("Request total profit for all by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForToday());
    }

    @GetMapping("/all-today-profit")
    public ResponseEntity getTodayProfitForAll(@AuthenticationPrincipal User user){
        logger.info("get today profit by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForToday());
    }

    @GetMapping("/all-current-month-profit")
    public ResponseEntity getCurrentMonthProfitForAll(@AuthenticationPrincipal User user){
        logger.info("get current month profit by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForCurrentMonth());
    }

    @GetMapping("/all-last-month-profit")
    public ResponseEntity getLastMonthProfitForAll(@AuthenticationPrincipal User user){
        logger.info("get last month profit by : " + user);
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForLastMonth());
    }



}



