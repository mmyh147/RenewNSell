package com.example.renewnsell.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class DTOResponseFixProduct {
    @NotEmpty(message = "Description can't be null")
    private String description;
    @Positive(message = "price must be positive")
    @Min(value = 100,message = "minimum price 100")
    private double price;
}
