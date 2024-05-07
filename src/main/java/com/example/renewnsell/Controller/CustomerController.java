package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.CustomerService;
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
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(FixProductController.class);

    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        logger.info("retrieve all customers");
        if (customerService.getAll().isEmpty()){
            throw new ApiException("No customer found");
        }
        return ResponseEntity.ok(customerService.getAll());
    }
    //================================= By Mohammed Alhajri ===================================

    @PostMapping("/add")
    public ResponseEntity addProfile(@RequestBody @Valid CustomerDTO customerDTO){
        logger.info("register new customer");
        customerService.register(customerDTO);
        return ResponseEntity.ok(new ApiResponse("customer added"));
    }
    //================================= By Mohammed Alhajri ===================================

    @PutMapping("/update/{id}")
    public ResponseEntity update(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody @Valid CustomerDTO customerDTO){
        logger.info("update customer with ID : " + id);

        customerService.update(id, customerDTO);
        return ResponseEntity.ok(new ApiResponse("Customer updated"));
    }

    @PutMapping("/update-my-user")
    public ResponseEntity updateMyUser(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO customerDTO){
        logger.info("update customer with ID : " + user.getId());

        customerService.updateMyUser(user, customerDTO);
        return ResponseEntity.ok(new ApiResponse("Customer updated"));
    }
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-customer-by-id/{id}")
    public ResponseEntity getCustomerById(@PathVariable Integer id){
        logger.info("get customer with ID : " + id);


        return ResponseEntity.ok(customerService.getCustomerByID(id));
    }

    //================================= By Ghaliah ===================================
    @GetMapping("/total-customers-by-gender/{gender}")
    public ResponseEntity totalFemalesCustomers(@AuthenticationPrincipal @PathVariable String gender) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.totalCustomersByGender(gender));
    }

}
