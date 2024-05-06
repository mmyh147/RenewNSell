package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

private final ProductService productService;
    @PostMapping("/add")
    public ResponseEntity addProduct(@AuthenticationPrincipal User user, @RequestBody @Valid Product product) {
        productService.addProduct(user.getId(), product);
        return ResponseEntity.ok(new ApiResponse("created Product"));
    }
    //GHALIAH
    @GetMapping("get-availability-of-product/{productId}")
    public ResponseEntity checkAvailabilityProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
        return ResponseEntity.ok(productService.checkAvailabilityProduct(productId));
    }
//totalProductForCompany//getProductForCompany

    @GetMapping("/get-total-product-for-company/")
    public ResponseEntity totalProductForCompany(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(productService.totalProductForCompany(user.getId()));
    }

    @GetMapping("/get-all-product-for-company/")
    public ResponseEntity allProductForCompany(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductForCompany(user.getId()));
    }

}
