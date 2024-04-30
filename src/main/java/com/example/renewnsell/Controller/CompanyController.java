package com.example.renewnsell.Controller;

import com.example.renewnsell.DTO.CompanyDTO;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Service.CompanyService;
import com.example.renewnsell.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {


    private final CompanyService companyService;

    @PostMapping("/add")
    public ResponseEntity addProfile(@RequestBody @Valid CompanyDTO companyDTO){
        companyService.register(companyDTO);
        return ResponseEntity.ok("Company added");
    }
}
