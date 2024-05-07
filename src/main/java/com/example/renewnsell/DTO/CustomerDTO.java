package com.example.renewnsell.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

  //  @NotEmpty(message = "username must not be empty")
    private String username;

  //  @NotEmpty(message = "password must not be empty")
    private String password;

   // @NotEmpty(message = "name must not be empty")
    private String name;

//    @NotEmpty(message = "email must not be empty")
//    @Email(message = "email must be valid")
    private String email;

//    @NotEmpty(message = "phone must not be empty")
//    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "gender not be empty")
    @Pattern(regexp = "F|M", message = "gender must be FEMALE or MALE ")
    private String gender;
}