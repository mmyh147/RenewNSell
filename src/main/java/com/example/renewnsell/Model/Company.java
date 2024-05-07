package com.example.renewnsell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "commercial License must not be empty")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String commercialLicense;
    @NotEmpty(message = "industry License must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String industry;
    @NotEmpty(message = "logoPath License must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String logoPath;




    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Review> reviews;
//
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<OrderCompany> orders;


}
