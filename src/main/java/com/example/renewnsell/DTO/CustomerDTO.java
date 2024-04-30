package com.example.renewnsell.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @NotEmpty(message = "must not be empty")
    private String username;

    @NotEmpty(message = "must not be empty")
    private String password;

    @NotEmpty(message = "must not be empty")
    private String name;

    @NotEmpty(message = "must not be empty")
    private String email;

    @NotEmpty(message = "must not be empty")
    private String phoneNumber;

    @NotEmpty(message = "gender not be empty")
    private String gender;
}