package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProduct {
//-totalprice:int
//-status:String
//-tax:int
//-date:LocalDate
//-totalItems:int
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@NotNull(message = "Total Price can't be null")
    private double totalPrice;
   // @NotEmpty(message = "Status can't be null")
   // @Column(columnDefinition = " status varchar(50) not null")
   //@Pattern(regexp = "PENDING|PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY")
    private String status;
//    @NotNull(message = "Tax can't be null")
//    @Column(columnDefinition = "tax int not null")
    private Double tax;
//    @NotNull(message = "Date can't be null")
//    @Column(columnDefinition = "date datetime not null")
    private LocalDate date;
//    @NotNull(message = "Total Items can't be null")
//    @Column(columnDefinition = "totalItems int not null")
    private Integer totalItems;


    //-------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

//
//     @OneToMany(cascade = CascadeType.ALL,mappedBy = "orderProduct")
//    private Set<Product>productSet;


    @ManyToMany(mappedBy = "orderProduct")
    private Set<Product>products;
    @OneToOne(mappedBy = "orderProduct", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FixProduct fixProduct;


}
