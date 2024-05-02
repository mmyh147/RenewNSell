package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

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
   // @Positive(message = "must not be positive")
//    @Column(columnDefinition = "int not null")
    private Integer quantity;

    //    @Column(columnDefinition = "boolean")
    private Boolean isAppear;
    private Boolean buyWithFix;

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

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderProduct orderProduct;
}
