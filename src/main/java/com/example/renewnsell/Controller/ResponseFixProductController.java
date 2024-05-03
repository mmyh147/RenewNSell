package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.DTO.DTOResponseFixProduct;
import com.example.renewnsell.DTO.FixProductDTO;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ResponseFixProductService;
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
@RequestMapping("api/v1/response-fix-product")
public class ResponseFixProductController {

    private final ResponseFixProductService responseFixProductService;
    Logger logger= LoggerFactory.getLogger(ResponseFixProductController.class);


    @GetMapping("/get-all-response-request-fix-product")
    public ResponseEntity getAllResponseFixProduct(){
        logger.info("get-all-response-fix-product");
        return ResponseEntity.status(HttpStatus.OK).body(responseFixProductService.getAll());
    }
    @PostMapping("/response-request-fix-product/{fixProductId}")
    public ResponseEntity responseFixProductFromEmployee( @PathVariable Integer fixProductId, @RequestBody DTOResponseFixProduct responseFixProduct){
        logger.info("response-request-fix-product/{fixProductId}/{price}");
        responseFixProductService.response(fixProductId,responseFixProduct);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Send to customer Successfully"));
    }



    @PutMapping("/update-response-request-fix-product/{fixproductId}")
    public ResponseEntity updateResponseFixProduct( @PathVariable Integer fixproductId,@RequestBody @Valid DTOResponseFixProduct responseFixProduct){
        logger.info("response-fix-product");
        responseFixProductService.update(fixproductId,responseFixProduct);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("updated response fix product Successfully"));
    }
    @PutMapping("/delete-request-fix-product/{fixProductId}")
    public ResponseEntity deleteresponseResponseFixProduct( @PathVariable Integer fixproductId,@RequestBody @Valid DTOResponseFixProduct responseFixProduct){
        logger.info("response-fix-product");
        responseFixProductService.delete(fixproductId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("deleted response fix product Successfully"));
    }

}
