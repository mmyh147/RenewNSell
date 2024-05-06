package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @NotNull(message = "cant be null")
    @Column(columnDefinition = "int not null")
    private Integer rating;


    @NotEmpty(message = " description must not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String description;

    @Column(columnDefinition = "date")
    private LocalDate createdAt;

    @Column(columnDefinition = "date")
    private LocalDate updatedAt;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;
}
