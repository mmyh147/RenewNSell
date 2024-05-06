package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {


        private final ProductService productService;



        @GetMapping("get")
        public ResponseEntity getAllProduct() {
            return ResponseEntity.ok(productService.getAllProduct());
        }

        @PostMapping("/add")
        public ResponseEntity addProduct(@AuthenticationPrincipal User user, @RequestBody @Valid Product product) {
            productService.addProduct(user.getId(), product);
            return ResponseEntity.ok(new ApiResponse("createdProduct"));
        }


        @GetMapping("/get-all")
        public ResponseEntity getAllProductsByCompanyId(@AuthenticationPrincipal User user) {
            return ResponseEntity.ok(productService.getAllProductByCompanyId(user.getId()));

        }



        @GetMapping("/get-product/{productId}")
        public ResponseEntity getProductById(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
            Product product = productService.getProductById(productId, user.getId());
            return ResponseEntity.ok(product);
        }



        @PutMapping("/update/{productId}") // السيكيورتي ماضبط
        public ResponseEntity updateProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId, @RequestBody @Valid Product product) {
            productService.updateProduct(user.getId(), productId, product);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully"));
        }



        @DeleteMapping("/delete/{productId}")// السيكيورتي ماضبط
        public ResponseEntity deleteProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
            productService.deleteProduct(user.getId(), productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully"));
        }


        @GetMapping("/get-product-name/{name}")
        public ResponseEntity getProductByName( @PathVariable String name) {
            Product product = productService.getProductByName(name);
            return ResponseEntity.ok(product);
        }


        @GetMapping("/get-product-cname/{name}")
        public ResponseEntity getProductByCompanyName(@PathVariable String name) {
            Set<Product> products = productService.getProductByCompanyName( name);
            return ResponseEntity.ok(products);
        }


        @GetMapping("/get-product-defective/{percentOfDefective}")
        public ResponseEntity getProductByPercentOfDefective( @PathVariable Double percentOfDefective) {
            List<Product> products = productService.getProductByPercentOfDefective(percentOfDefective);
            return ResponseEntity.ok(products);
        }


    @GetMapping("/get-product-category/{category}")
    public ResponseEntity getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductByCategory( category);
        return ResponseEntity.ok(products);
    }


}
