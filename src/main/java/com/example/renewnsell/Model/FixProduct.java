package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FixProduct {
    //-id:int
//-description:String
//-status:String
//-category:String
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //-description:String
//@Column(columnDefinition = "description varchar(200) not null")
    private String description;
//PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY
//@Column(columnDefinition = "status varchar(200) not null check(status='PENDING' or status='PREPARING' or status='SHIPPED' or status='DELIVERED' or  status='ORDER_CONFIRMED' or  status='OUT_OF_DELIVERY')")
    private String status;
    //"SHOES|BAG|HAT|DRESS
   // @Column(columnDefinition = "category varchar(20) not null check(category='SHOES' or category='BAG' or category='HAT' or  category='DRESS')")
    private String category;


    //================================================
    @OneToOne
    @MapsId
    @JsonIgnore
    private OrderProduct orderProduct;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "fixProduct", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ResponseFixProduct responseFixProduct;
}
