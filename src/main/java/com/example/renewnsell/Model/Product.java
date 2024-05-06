package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = " name must not be empty")
//    @Column(columnDefinition = "varchar(10) not null")
    private String name;
//
//    @NotEmpty(message = " description must not be empty")
//    @Column(columnDefinition = "varchar(50) not null")
    private String description;

//    @NotNull(message = "priceBefore must not be null")
//    @Column(columnDefinition = "double not null")
    private Double priceBefore;

//
//    @NotNull(message = "price must not be null")
//    @Column(columnDefinition = "double not null")
    private Double price;

//    @NotNull(message = "must not be null")
//    @Column(columnDefinition = "int not null")
    private Integer quantity;

//    @NotNull(message = "cant be null")
//    @Column(columnDefinition = "boolean")
    private Boolean isAppear;


//    @NotEmpty(message = " category must not be empty")
//    @Column(columnDefinition = "varchar(10) not null")
    private String category;


//    @NotNull(message = "must not be null")
//    @Column(columnDefinition = "double not null")
    private Double fixPrice;

//    @NotNull(message = "must not be null")
//    @Column(columnDefinition = "double not null")
    private Double percentOfDefective;





    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;
}
