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
    Logger logger = LoggerFactory.getLogger(FixProductController.class);


    @GetMapping("/get-all-request-fix-product")
    public ResponseEntity getAllFixProduct() {
        logger.info("get-all-request-fix-product");
        return ResponseEntity.status(HttpStatus.OK).body(fixProductService.getAll());
    }

    @PostMapping("/request-fix-product")//as add
    public ResponseEntity addFixProduct(@AuthenticationPrincipal User user, @RequestBody @Valid FixProductDTO fixProductDTO) {
        logger.info("request-fix-product");
        fixProductService.addFixProduct(user.getId(), fixProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Send request fix product Successfully"));
    }
@PutMapping("/update/{fixId}")
    public ResponseEntity update(@AuthenticationPrincipal User user,@PathVariable Integer fixId, @RequestBody @Valid FixProductDTO fixProductDTO) {
        logger.info("update request-fix-product");
        fixProductService.update(user.getId(), fixId,fixProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Send request fix product Successfully"));
    }
    @DeleteMapping("/delete-fix/{fixId}")
    public ResponseEntity delete(@PathVariable Integer fixId) {
        fixProductService.delete(fixId);
        return ResponseEntity.status(200).body(new ApiResponse("delete successfully"));
    }


    //================================ 1-First Endpoint in FixProduct ==========================================
    @PostMapping("/accept-price-fix-product/{fixProductId}")
    public ResponseEntity acceptPriceFixProduct(@AuthenticationPrincipal User user, @PathVariable Integer fixProductId) {
        logger.info("accept-price-fix-product/{fixProductId}");
        fixProductService.acceptPriceFixProduct(user.getId(), fixProductId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("accept Successfully"));
    }

    //================================ 2-Second Endpoint in FixProduct ==========================================
    @PostMapping("/reject-price-fix-product/{fixProductId}")
    public ResponseEntity rejectPriceFixProduct(@AuthenticationPrincipal User user, @PathVariable Integer fixProductId) {
        logger.info("reject-price-fix-product/{fixProductId}");
        fixProductService.rejectPriceFixProduct(user.getId(), fixProductId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Reject Successfully"));
    }


    //================================ 3-Third Endpoint in FixProduct ==========================================

    @GetMapping("/get-fix-product/{fixProductId}")
    public ResponseEntity getFixProductOne(@AuthenticationPrincipal User user, @PathVariable Integer fixProductId) {
        logger.info("get-fix-product/{fixProductId}");
        return ResponseEntity.status(HttpStatus.OK).body(fixProductService.getFixProductOne(user.getId(), fixProductId));
    }
    //================================ Total Endpoints in FixProduct is 3 ==========================================
}
