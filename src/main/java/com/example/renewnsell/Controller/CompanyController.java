package com.example.renewnsell.Controller;

import com.example.renewnsell.DTO.CompanyDTO;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Service.CompanyService;
import com.example.renewnsell.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {


    private final CompanyService companyService;


    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        return ResponseEntity.ok(companyService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity addProfile(@RequestBody @Valid CompanyDTO companyDTO){
        companyService.register(companyDTO);
        return ResponseEntity.ok("Company added");
    }
}
