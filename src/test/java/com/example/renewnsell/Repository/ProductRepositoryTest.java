package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {


    //HAYA

    @Autowired
    ProductRepository productRepository;


    Product product1,product2,product3;

    User user;
    Company company;
    Product product;

    Set<OrderCompany> orderCompanies;

    Set<OrderProduct> orderProducts;


    List<Product> productList=new ArrayList<>();
    Set<Product> products=new HashSet<>(productList);

    @BeforeEach
    void setUp() {
        user = new User(null,"HayaAlajaleen","112233","haya","0544323211","haya@gmail.com","Admin",company,null);
        product1=new Product(null,"SHOES","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        product2=new Product(null,"SHOES2","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        product3=new Product(null,"SHOES3","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        company=new Company(null,"38822992","fashion","dghsj.png",null,products,null,null);
    }


    @Test
    public void findAllProductByCompanyId(){
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productList=productRepository.findAllProductByCompanyId(company.getId());
        Assertions.assertThat(productList.get(0).getCompany().getId()).isEqualTo(company.getId());
    }


    @Test
    public void findProductById() {
        productRepository.save(product1);
        product=productRepository.findProductById(product1.getId());
        Assertions.assertThat(product).isEqualTo(product1);
    }


    @Test
    public void findProductByName() {
        productRepository.save(product1);
        product=productRepository.findProductByName(product1.getName());
        Assertions.assertThat(product).isEqualTo(product1);
    }









    }
