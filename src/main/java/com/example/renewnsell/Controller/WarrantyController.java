package com.example.renewnsell.Controller;

import com.example.renewnsell.DTO.CompanyDTO;
import com.example.renewnsell.Model.Warranty;
import com.example.renewnsell.Service.WarrantyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/warranty")
public class WarrantyController {


    private final WarrantyService warrantyService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Warranty warranty){
        warrantyService.newWarranty(warranty);
        return ResponseEntity.ok("warranty added");
    }
}
