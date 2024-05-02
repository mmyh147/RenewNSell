package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.DTO.FixProductDTO;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.FixProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
//-----------------------------------Ghaliah----------------------------------

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/fix-product")
public class FixProductController {

    private final FixProductService fixProductService;
    Logger logger= LoggerFactory.getLogger(FixProductController.class);
@PostMapping("/request-fix-product")
public ResponseEntity addFixProduct(@AuthenticationPrincipal User user,@RequestBody @Valid FixProductDTO fixProductDTO){
    logger.info("request-fix-product");
    fixProductService.addFixProduct(user.getId(),fixProductDTO);
    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Send request fix product Successfully"));
}
    @GetMapping("/get-all-request-fix-product")
    public ResponseEntity getAllFixProduct(){
        logger.info("get-all-request-fix-product");
        return ResponseEntity.status(HttpStatus.OK).body(fixProductService.getAll());
    }

    //response
    @PostMapping("/response-request-fix-product/{fixProductId}/{price}")
public ResponseEntity responseFixProductFromEmployee( @PathVariable Integer fixProductId,@PathVariable Double price, @RequestBody String description){
        logger.info("response-request-fix-product/{fixProductId}/{price}");
        fixProductService.response(fixProductId,price,description);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Send to user Successfully"));
}
//acceptPriceFixProduct
    @PostMapping("/accept-price-fix-product/{fixProductId}")
    public ResponseEntity acceptPriceFixProduct(@AuthenticationPrincipal User user,@PathVariable Integer fixProductId ){
        logger.info("accept-price-fix-product/{fixProductId}");
        fixProductService.acceptPriceFixProduct(user.getId(),fixProductId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("accept Successfully"));
    }
//rejectPriceFixProduct
@PostMapping("/reject-price-fix-product/{fixProductId}")
public ResponseEntity rejectPriceFixProduct(@AuthenticationPrincipal User user,@PathVariable Integer fixProductId ){
    logger.info("reject-price-fix-product/{fixProductId}");
    fixProductService.rejectPriceFixProduct(user.getId(),fixProductId);
    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Reject Successfully"));
}
    //changeStatus

    @PostMapping("/change-status-of-fix-product/{fixProductId}")
    public ResponseEntity changeStatus(@PathVariable Integer fixProductId ){
        logger.info("accept-price-fix-product/{fixProductId}");
        fixProductService.changeStatus(fixProductId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Change status Successfully"));
    }
//getStatusOfFixProduct
    @GetMapping("/get-status-of-fix-product/{fixProductId}")
    public ResponseEntity getStatusOfFixProduct(@AuthenticationPrincipal User user,@PathVariable Integer fixProductId){
        logger.info("get-status-of-fix-product/{fixProductId}");
        return ResponseEntity.status(HttpStatus.OK).body(fixProductService.getStatusOfFixProduct(user.getId(),fixProductId));
    }

    //getFixProductOne

    @GetMapping("/get-fix-product/{fixProductId}")
    public ResponseEntity getFixProductOne(@AuthenticationPrincipal User user,@PathVariable Integer fixProductId){
        logger.info("get-fix-product/{fixProductId}");
        return ResponseEntity.status(HttpStatus.OK).body(fixProductService.getFixProductOne(user.getId(),fixProductId));
    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity delete( @PathVariable Integer orderId){
        fixProductService.delete(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }
}
