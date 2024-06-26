package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductController {



    //HAYA


    @MockBean
    ProductService productService;


    @Autowired
    MockMvc mockMvc;

    Product product1,product2,product3;

    User user;
    Company company;

    Set<OrderCompany> orderCompanies;

    Set<OrderProduct> orderProducts;

    ApiResponse apiResponse;


    List<Product> productList=new ArrayList<>();
    Set<Product> products=new HashSet<>(productList);


    @BeforeEach
    void setUp() {
        user = new User(null,"HayaAlajaleen","112233","haya","0544323211","haya@gmail.com","Admin",company,null);
        product1=new Product(null,"SHOES","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        product2=new Product(null,"SHOES2","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        product3=new Product(null,"SHOES3","SS",5.44,3.22,12,true,"SHOES",2.3,33.3,company,orderCompanies,orderProducts);
        company=new Company(null,"38822992","fashion","dghsj.png",null,products,null,null);

        List<Product> productList=new ArrayList<>();
        products=new HashSet<>(productList);
        products.add(product1);
        products.add(product2);
        products.add(product3);

    }


    @Test
    public void AddProduct() throws Exception {
        mockMvc.perform(post("api/v1/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product1)))
                .andExpect(status().isOk());
    }



    @Test
    public void getAllProducts() throws Exception {
        Mockito.when(productService.getAllProduct()).thenReturn(productList);
        mockMvc.perform(get("/api/v1/product/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("product"));
    }









}
