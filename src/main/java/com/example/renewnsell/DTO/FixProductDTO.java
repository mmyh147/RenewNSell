package com.example.renewnsell.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class FixProductDTO {

    @NotEmpty(message = "Description can't be null")
    private String description;
    @Pattern(regexp = "PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY")
    private String status;//first creation status pending so customer not write that in postman, so it can be null by customer but in CRUD automated pending
    // shoes , bag, hat, dress;
    @Pattern(regexp = "SHOES|BAG|HAT|DRESS")
    @NotEmpty(message = "Category can't be null")
    private String category;
}
