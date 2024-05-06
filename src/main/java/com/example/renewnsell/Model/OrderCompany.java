package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class OrderCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Column(columnDefinition = "  double not null")
    //@NotNull(message = "Total Price can't be null")
    private double totalPrice;
    // @NotEmpty(message = "Status can't be null")
    // @Column(columnDefinition = "  varchar(50) not null")
    //@Pattern(regexp = "PENDING|PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_FOR_DELIVERY")
    private String status;
    // @Column(columnDefinition = "  boolean not null")
    private Boolean buyWithFix;
    //@NotNull(message = "Date can't be null")
    //@Column(columnDefinition = " datetime not null")

    private LocalDate date;


    @ManyToMany(mappedBy = "orderCompany")
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Company company;


    @ManyToOne
    @JoinColumn(name = "orderProduct_id")
    @JsonIgnore
    private OrderProduct orderProduct;
}
