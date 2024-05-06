package com.example.renewnsell.Service;

import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.ProductRepository;
import com.example.renewnsell.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {


    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    UserRepository userRepository;


    User user;
    Product product1,product2,product3;

    List<Product> productList=new ArrayList<>();
    Set<Product> products=new HashSet<>(productList);
    Company company;

    @BeforeEach
    void setUp() {
        user = new User(null,"HayaAlajaleen","112233","haya","0544323211","haya@gmail.com","Admin",company,null);
        product1=new Product(null,"SHOES","shoes1",44.32,33.2,4,true,"shoes",1.0,2.0,company);
        product2=new Product(null,"SHOES","shoes2",44.32,33.2,4,true,"shoes",1.0,2.0,company);
        product3=new Product(null,"SHOES","shoes3",44.32,33.2,4,true,"shoes",1.0,2.0,company);

        company=new Company(null,"38822992","fashion","dghsj.png",null,products,null,null);
        List<Product> productList=new ArrayList<>();
        products=new HashSet<>(productList);
        products.add(product1);
        products.add(product2);
        products.add(product3);

    }


    @Test
    public void getAllProductByCompanyId(){
        when(productRepository.findAllProductByCompanyId(company.getId())).thenReturn(productList);
        Set<Product> products1=productService.getAllProductByCompanyId(company.getId());
        Assertions.assertEquals(products.size(),products1.size());
        verify(productRepository,times(1)).findAllProductByCompanyId(company.getId());

    }


    @Test
    public void AddProduct() {
        when(companyRepository.findCompanyById(company.getId())).thenReturn(company);
        when(userRepository.findUserById(user.getId())).thenReturn(user);
        productService.addProduct(company.getId(),product3);
        verify(companyRepository,times(1)).findCompanyById(company.getId());
        verify(userRepository,times(1)).findUserById(user.getId());
        verify(productRepository,times(1)).save(product3);
    }


    @Test
    public void updateProduct() {
        when(companyRepository.findCompanyById(company.getId())).thenReturn(company);
        when(userRepository.findUserById(user.getId())).thenReturn(user);
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);

        productService.updateProduct(user.getId(), product1.getId(),product2);
        verify(companyRepository,times(1)).findCompanyById(company.getId());
        verify(productRepository,times(1)).findProductById(product2.getId());
        verify(userRepository,times(1)).findUserById(user.getId());
        verify(productRepository,times(1)).save(product2);
    }

    @Test
    public void deleteProduct() {
        when(companyRepository.findCompanyById(company.getId())).thenReturn(company);
        when(userRepository.findUserById(user.getId())).thenReturn(user);
        productService.deleteProduct(user.getId(), product1.getId());
        verify(companyRepository,times(1)).findCompanyById(company.getId());
        verify(productRepository,times(1)).findProductById(product1.getId());
        verify(userRepository,times(1)).findUserById(user.getId());
        verify(productRepository,times(1)).save(product1);

    }


    @Test
    public void getProductByCategory(){
        when(productRepository.findProductByCategory(product1.getCategory())).thenReturn(productList);

        List<Product> result = productService.getProductByCategory(product1.getCategory());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(products, result.get(0));

        verify(productRepository, times(1)).findProductByCategory(product1.getCategory());
    }




}
