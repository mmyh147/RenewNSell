package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.DTO.CompanyDTO;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.CompanyService;
import com.example.renewnsell.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {


    private final CompanyService companyService;
    Logger logger = LoggerFactory.getLogger(FixProductController.class);

    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        logger.info("retrieve all companies");

        return ResponseEntity.ok(companyService.getAll());
    }
    //================================= By Mohammed Alhajri ===================================

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid CompanyDTO companyDTO){
        logger.info("register new company with details : " + companyDTO);
        companyService.register(companyDTO);
        return ResponseEntity.ok(new ApiResponse("Company added"));
    }
    //================================= By Mohammed Alhajri ===================================

    @PutMapping("/update/{id}")
    public ResponseEntity update(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody @Valid CompanyDTO companyDTO){
        logger.info("update company");
        companyService.update(id, companyDTO);
        return ResponseEntity.ok(new ApiResponse("company updated"));
    }

    @PutMapping("/update-my-user")
    public ResponseEntity updateMyUser(@AuthenticationPrincipal User user, @RequestBody @Valid CompanyDTO companyDTO){
        logger.info("update company");
        companyService.updateMyUser(user, companyDTO);
        return ResponseEntity.ok(new ApiResponse("company updated"));
    }

    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-company-by-id/{id}")
    public ResponseEntity getCompanyById(@PathVariable Integer id){
        logger.info("request company with ID : " + id);
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }


}
