package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    Logger logger = LoggerFactory.getLogger(OrderCompanyController.class);

    //Endpoint 42
    //GHALIAH
    @GetMapping("get-availability-of-product/{productId}")
    public ResponseEntity checkAvailabilityProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
        return ResponseEntity.ok(productService.checkAvailabilityProduct(productId));
    }
//totalProductForCompany//getProductForCompany

    //Endpoint 43
    @GetMapping("/get-total-product-for-company/")
    public ResponseEntity totalProductForCompany(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(productService.totalProductForCompany(user.getId()));
    }
    //Endpoint 44
    @GetMapping("/get-all-product-for-company/")
    public ResponseEntity allProductForCompany(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductForCompany(user.getId()));
    }






              //Haya

        @GetMapping("get")
        public ResponseEntity getAllProduct() {
            logger.info("get-All-Product");
            return ResponseEntity.ok(productService.getAllProduct());
        }

        @PostMapping("/add")
        public ResponseEntity addProduct(@AuthenticationPrincipal User user, @RequestBody @Valid Product product) {
            productService.addProduct(user.getId(), product);
            logger.info("add-Product");

            return ResponseEntity.ok(new ApiResponse("createdProduct"));
        }


        @GetMapping("/get-all")
        public ResponseEntity getAllProductsByCompanyId(@AuthenticationPrincipal User user) {
            logger.info("get-All-Products-By-CompanyId");

            return ResponseEntity.ok(productService.getAllProductByCompanyId(user.getId()));

        }


    //Endpoint 45
        @GetMapping("/get-product/{productId}")
        public ResponseEntity getProductById(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
            Product product = productService.getProductById(productId, user.getId());
            logger.info("get-ProductById");
            return ResponseEntity.ok(product);
        }



        @PutMapping("/update/{productId}")
        public ResponseEntity updateProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId, @RequestBody @Valid Product product) {
            productService.updateProduct(user.getId(), productId, product);
            logger.info("update-Product");
            return ResponseEntity.ok(new ApiResponse("Product updated successfully"));
        }



        @DeleteMapping("/delete/{productId}")
        public ResponseEntity deleteProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
            productService.deleteProduct(user.getId(), productId);
            logger.info("delete-Product");
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully"));
        }

    //Endpoint 45
        @GetMapping("/get-product-name/{name}")
        public ResponseEntity getProductByName( @PathVariable String name) {
            Product product = productService.getProductByName(name);
            logger.info("get-ProductByName");
            return ResponseEntity.ok(product);
        }

    //Endpoint 46
        @GetMapping("/get-product-cname/{name}")
        public ResponseEntity getProductByCompanyName(@PathVariable String name) {
            Set<Product> products = productService.getProductByCompanyName( name);
            logger.info("get-ProductByCompanyName");
            return ResponseEntity.ok(products);
        }

    //Endpoint 47
        @GetMapping("/get-product-defective/{percentOfDefective}")
        public ResponseEntity getProductByPercentOfDefective( @PathVariable Double percentOfDefective) {
            List<Product> products = productService.getProductByPercentOfDefective(percentOfDefective);
            logger.info("get-ProductByPercentOfDefective");
            return ResponseEntity.ok(products);
        }

    //Endpoint 48
    @GetMapping("/get-product-category/{category}")
    public ResponseEntity getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductByCategory( category);
        logger.info("get-ProductByCategory");
        return ResponseEntity.ok(products);
    }


}
