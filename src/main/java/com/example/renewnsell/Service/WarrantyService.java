package com.example.renewnsell.Service;

import com.example.renewnsell.Model.Warranty;
import com.example.renewnsell.Repository.WarrantyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public void newWarranty(Warranty warranty){

        warrantyRepository.save(warranty);

    }

    public boolean isWarrantyValid(Warranty warranty) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(warranty.getStartDate()) && currentDate.isBefore(warranty.getEndDate());
    }

}
