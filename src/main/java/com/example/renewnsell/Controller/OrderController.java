package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.DTO.DTO_BUY;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;
    Logger logger= LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/get-all-orders")
    public ResponseEntity getAll(){
        logger.info("get-all");
       return ResponseEntity.status(200).body(orderService.getAll());
    }

    //================================ 1-First Endpoint in OrderProduct ==========================================
    @PostMapping("/buy-order")
    public ResponseEntity buy(@AuthenticationPrincipal User user, @RequestBody List<DTO_BUY> productIds){
        logger.info("buy-order");
        orderService.buy(user.getId(),productIds);
        return ResponseEntity.status(200).body(new ApiResponse("Buy successfully"));
    }
    @PutMapping("/update-order/{orderId}")
    public ResponseEntity update( @PathVariable Integer orderId,@RequestBody @Valid OrderProduct orderProduct){
        logger.info("update-order/{orderId}");
        orderService.updateOrder(orderId,orderProduct);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity delete( @PathVariable Integer orderId){
        logger.info("delete-order/{orderId}");
        orderService.delete(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }
    //================================ 2-Second Endpoint in OrderProduct ==========================================

    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity cancel(@AuthenticationPrincipal User customer, @PathVariable Integer orderId){
        logger.info("cancel-order/{orderId}");
        orderService.cancelOrder(customer.getId(),orderId);
        return ResponseEntity.status(200).body(new ApiResponse("canceled ordered successfully"));
    }
    //================================ 3-Third Endpoint in OrderProduct ==========================================
    @PostMapping("/change-status-of-order/{orderId}")
    public ResponseEntity changeStatusOrder(@PathVariable Integer orderId ){
        logger.info("change-status-of-order/{orderId}");
        orderService.changeStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Change status Successfully"));
    }
    //================================ 4-Fourth Endpoint in OrderProduct ==========================================

    @GetMapping("/get-all-by-status/{status}")
    public ResponseEntity getAllSHIPPEDOrder(@PathVariable String status){
        logger.info("get-all-shipped-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus(status));

    }
    //================================ 5-Fifth Endpoint in OrderProduct ==========================================



    @GetMapping("/track-order/{orderId}")
    public ResponseEntity track(@AuthenticationPrincipal User user,@PathVariable Integer orderId){
        logger.info("track-order/{orderId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.track(user.getId(),orderId));
    }
    //================================ 6-Fifth Endpoint in OrderProduct ==========================================
//truckForEmployee
    @GetMapping("/track-order-for-employee/{orderId}")
    public ResponseEntity truckForEmployee(@PathVariable Integer orderId){
        logger.info("track-order/{orderId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.track(orderId));
    }

    @GetMapping("/get-all-customer-order")
    public ResponseEntity findAllByCustomer_Id(@AuthenticationPrincipal User user){
        logger.info("get-all-customer-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllByCustomer_Id(user.getId()));
    }
    //================================ 7-Seventh Endpoint in OrderProduct ==========================================

    //getAllOrderByProductId
    @GetMapping("/get-all-order-by-product-id/{productId}")
    public ResponseEntity getAllOrderByProductId(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-order-by-product-id/{productId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrderByProductId(productId));
    }
//getTotalNumberOfOrdersWebsite
    //================================ 8-Eighth Endpoint in OrderProduct ==========================================


    @GetMapping("/get-total-orders")
    public ResponseEntity totalOrders(){
        logger.info("/get-total-orders");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.totalOrders());
    }
    //================================ Total Endpoints in OrderProduct is 8 ==========================================


    //================================= By Mohammed Alhajri ===================================
    @GetMapping("/get-total-revenue")
    public ResponseEntity totalRevenue(){
        logger.info("Request total revenue");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllRevenueForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-today-revenue")
    public ResponseEntity todayRevenue(){
        logger.info("Request today revenue");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTodayRevenueForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-current-month-revenue")
    public ResponseEntity currentMonthRevenue(){
        logger.info("Request current month revenue");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getCurrentMonthRevenueForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-last-month-revenue")
    public ResponseEntity lastMonthRevenue(){
        logger.info("Request current month revenue");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getLastMonthRevenueForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/count-all-sold-product")
    public ResponseEntity countAllSoldProduct(){
        logger.info("Request count of sold product");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.countAllProductSoldForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/count-today-sold-product")
    public ResponseEntity countTodaySoldProduct(){
        logger.info("Request count of today sold product");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.countTodayProductSoldForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/count-current-month-sold-product")
    public ResponseEntity countCurrentMonthSoldProduct(){
        logger.info("Request count of current month sold product");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.countCurrentMonthProductSoldForWebsite());
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/count-last-month-sold-product")
    public ResponseEntity countLastMonthSoldProduct(){
        logger.info("Request count of last month sold product");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.countLastMonthProductSoldForWebsite());
    }

}

