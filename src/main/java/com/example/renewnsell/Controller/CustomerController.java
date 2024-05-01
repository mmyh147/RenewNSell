package com.example.renewnsell.Controller;

import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {


    private final CustomerService customerService;


    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        return ResponseEntity.ok(customerService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity addProfile(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.register(customerDTO);
        return ResponseEntity.ok("customer added");
    }

    @PutMapping("/update/{account_id}")
    public ResponseEntity update(@AuthenticationPrincipal User user, @PathVariable Integer account_id, @RequestBody @Valid CustomerDTO customerDTO){
        customerService.update(account_id, customerDTO);
        return ResponseEntity.ok("Customer updated");
    }

}
