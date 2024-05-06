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

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        logger.info("get-all");
       return ResponseEntity.status(200).body(orderService.getAll());
    }
    @PostMapping("/buy-order")
    public ResponseEntity buy(@AuthenticationPrincipal User user, @RequestBody List<DTO_BUY> productIds){
        logger.info("buy-order");
        orderService.buy(user.getId(),productIds);
        return ResponseEntity.status(200).body(new ApiResponse("Buy successfully"));
    }

    @PutMapping("/update-order/{orderId}")
    public ResponseEntity update(@AuthenticationPrincipal User company, @RequestBody @Valid OrderProduct orderProduct){
        logger.info("update-order/{orderId}");
        orderService.updateOrder(company.getId(),orderProduct);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity delete( @PathVariable Integer orderId){
        logger.info("delete-order/{orderId}");
        orderService.delete(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity cancel(@AuthenticationPrincipal User customer, @PathVariable Integer orderId){
        logger.info("cancel-order/{orderId}");
        orderService.cancelOrder(customer.getId(),orderId);
        return ResponseEntity.status(200).body(new ApiResponse("canceled ordered successfully"));
    }

    @PostMapping("/change-status-of-order/{orderId}")
    public ResponseEntity changeStatusOrder(@PathVariable Integer orderId ){
        logger.info("change-status-of-order/{orderId}");
        orderService.changeStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Change status Successfully"));
    }
// i use this method getAllByStatus for all pattern
    //@Pattern(regexp = "PENDING|PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY")

    @GetMapping("/get-all-canceled-order")
    public ResponseEntity getAllCanceledOrder(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus("CANCELED"));

    }
    @GetMapping("/get-all-shipped-order")
    public ResponseEntity getAllSHIPPEDOrder(){
        logger.info("get-all-shipped-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus("SHIPPED"));

    }

    @GetMapping("/get-all-out-of-delivery-order")
    public ResponseEntity getAllOUT_OF_DELIVERYOrder(){
        logger.info("get-all-out-of-delivery-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus("OUT_OF_DELIVERY"));

    }
//DELIVERED
@GetMapping("/get-all-delivered-order")
public ResponseEntity getAllDELIVEREDOrder(){
    logger.info("get-all-delivered-order");
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus("DELIVERED"));

}
    @GetMapping("/get-all-rejected-order")
    public ResponseEntity getAllRejectedOrder(){
        logger.info("get-all-rejected-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllByStatus("REJECTED"));

    }


    @GetMapping("/get-status-of-order/{orderId}")
    public ResponseEntity getStatusOfFixProduct(@AuthenticationPrincipal User user,@PathVariable Integer orderId){
        logger.info("get-status-of-fix-product/{fixProductId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getStatusOfOrder(user.getId(),orderId));
    }

    @GetMapping("/truck-order/{orderId}")
    public ResponseEntity track(@PathVariable Integer orderId){
        logger.info("truck-order/{orderId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.truck(orderId));
    }

    //findAllByCustomer_Id
    @GetMapping("/get-all-customer-order/")
    public ResponseEntity findAllByCustomer_Id(@AuthenticationPrincipal User user){
        logger.info("get-all-customer-order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllByCustomer_Id(user.getId()));
    }


    //getAllOrderByProductId
    @GetMapping("/get-all-order-by-product-id/{productId}")
    public ResponseEntity getAllOrderByProductId(@AuthenticationPrincipal User user,@PathVariable Integer productId){
        logger.info("/get-all-order-by-product-id/{productId}");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrderByProductId(productId));
    }


//    @GetMapping("/profit")
//    public ResponseEntity getAllProfit(){
//        logger.info("/profit");
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllTimeProfitForAll());
//    }
//
//    @GetMapping("/company-profit/{company_id}")
//    public ResponseEntity getAllCompanyProfit(@PathVariable Integer company_id){
//        logger.info("/company-profit/{company_id}");
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllTimeProfitForCompany(company_id));
//    }
//
//    @GetMapping("/company-profit-today/{company_id}")
//    public ResponseEntity getTodayCompanyProfit(@PathVariable Integer company_id){
//        logger.info("/company-profit/{company_id}");
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTodayProfitForCompany(company_id));
//    }
//
//    @GetMapping("/profit-today")
//    public ResponseEntity getTodayCompanyProfit(){
//        logger.info("/company-profit/{company_id}");
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTodayProfitForAll());
//    }
//
//    @GetMapping("/profit-current-month/{company_id}")
//    public ResponseEntity getCurrentMonthCompanyProfit(@PathVariable Integer company_id){
//        logger.info("/company-profit/{company_id}");
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getCurrentMonthProfitForCompany(company_id));
//    }
}

