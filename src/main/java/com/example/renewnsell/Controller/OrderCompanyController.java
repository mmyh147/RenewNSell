package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.OrderCompany;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.OrderCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update-order-company/{orderId}")
    public ResponseEntity update(@AuthenticationPrincipal User company, @PathVariable Integer orderId,@RequestBody @Valid OrderCompany orderCompany){
        orderCompanyService.update(company.getId(),orderId,orderCompany);
        logger.info("/update-order-");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("updated order company successfully "));


    }
    @DeleteMapping("/delete-order-company/{orderId}")
    public ResponseEntity delete(@AuthenticationPrincipal User company, @PathVariable Integer orderId){
        orderCompanyService.delete(company.getId(),orderId);
        logger.info("/update-order-");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("deleted order company successfully "));


    }

    //===============================CRUD DONE BY GHALIAH======================

    //================================ 1-First Endpoint in OrderCompany ==========================================

    @PostMapping("/change-status-of-order-company/{orderId}")
    public ResponseEntity changeStatusOrder(@AuthenticationPrincipal User user,@PathVariable Integer orderId ){
        logger.info("change-status-of-order/{orderId}");
        orderCompanyService.changeStatus(user.getId(), orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Change status Successfully"));
    }
    //===========================

    //getTodayProfitForCompany
    //================================ 2-Second Endpoint in OrderCompany ==========================================

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

    //getOrderProductByPercentOfDefective
    //================================ 3-Third Endpoint in OrderCompany ==========================================

    @GetMapping("/get-all-orders-company-of-percent-of-defective/{precent}")
    public ResponseEntity getOrderProductByPercentOfDefective(@AuthenticationPrincipal User user, @PathVariable Double precent){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getOrderProductByPercentOfDefective(user.getId(),precent));
    }
    //================================ 4-Fourth Endpoint in OrderCompany ==========================================
    @GetMapping("/get-total-orders-company")
    public ResponseEntity getTotalNumberOfOrdersCompany(@AuthenticationPrincipal User user){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalNumberOfOrdersCompany(user.getId()));
    }
    //================================ 5-Fifth Endpoint in OrderCompany ==========================================

    @GetMapping("/get-total-profit-of-one-product/{productId}")
    public ResponseEntity getTotalProfitForOneProduct(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForOneProduct(user.getId(),productId));
    }
    //================================ 6-Sixth Endpoint in OrderCompany ==========================================

        @GetMapping("/get-total-profit-of-one-product-with-out-fix/{productId}")
    public ResponseEntity getTotalProfitForOneProductWithOutFixPrice(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForOneProductWithOutFixPrice(user.getId(),productId));
    }
    //================================ 7-Seventh Endpoint in OrderCompany ==========================================

    @GetMapping("/get-total-profit-of-one-product-with-fix/{productId}")
    public ResponseEntity getTotalProfitForOneProductWithFixPrice(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalProfitForOneProductWithFixPrice(user.getId(),productId));
    }


    //================================ 8-Eighth Endpoint in OrderCompany ==========================================

    @GetMapping("/get-total-number-of-order-for-one-product/{productId}")
    public ResponseEntity getTotalNumberOrdersForOneProduct(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-total-price-orders-by-company-id");
        return ResponseEntity.status(HttpStatus.OK).body(orderCompanyService.getTotalNumberOrdersForOneProduct(user.getId(),productId));
    }

    //================================ Total Endpoints in OrderCompany is 8 ==========================================


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



