package com.example.renewnsell.Controller;

import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

private final ProductService productService;
    @PostMapping("/add")
    public ResponseEntity addProduct(@AuthenticationPrincipal User user, @RequestBody @Valid Product product) {
        productService.addProduct(user.getId(), product);
        return ResponseEntity.ok("createdProduct");
    }
}
