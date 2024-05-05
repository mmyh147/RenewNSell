package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.Warranty;
import com.example.renewnsell.Repository.WarrantyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public void newWarranty(Warranty warranty){

        warrantyRepository.save(warranty);

    }

    public boolean isWarrantyValid(Integer warrantyId) {
        Warranty warranty = warrantyRepository.findWarrantyById(warrantyId);
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isBefore(warranty.getStartDate()) && !currentDate.isAfter(warranty.getEndDate());
    }

    public long getDaysLeftForWarranty(Integer warrantyId) {
        Warranty warranty = warrantyRepository.findWarrantyById(warrantyId);
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(warranty.getStartDate())) {
            throw new ApiException("Warranty has not started yet");
        } else if (currentDate.isBefore(warranty.getEndDate())) {

            return ChronoUnit.DAYS.between(currentDate, warranty.getEndDate());
        } else {
            throw new ApiException("Warranty Expired");
        }
    }

    public void extendWarranty(Integer warrantyId, Integer daysToExtend) {
        if (1 > daysToExtend){
            throw new ApiException("please enter positive number");
        }
        Warranty warranty = warrantyRepository.findWarrantyById(warrantyId);
        LocalDate newEndDate = warranty.getEndDate().plusDays(daysToExtend);
        warranty.setEndDate(newEndDate);
        warrantyRepository.save(warranty);

    }


}
