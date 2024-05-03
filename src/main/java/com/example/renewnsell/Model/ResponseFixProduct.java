package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseFixProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //-description:String
//@Column(columnDefinition = "description varchar(200) not null")
    private String description;

    // @Column(columnDefinition = "price double not null ")
    private double price;

    @OneToOne
    @MapsId
    @JsonIgnore
    private FixProduct fixProduct;
}
