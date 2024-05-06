package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.OrderCompanyRepository;
import com.example.renewnsell.Repository.ProductRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final OrderCompanyRepository orderCompanyRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public void addProduct(Integer userId, Product product) {
        User user = userRepository.findUserById(userId);
        Company company = companyRepository.findCompanyById(user.getId());
        product.setCompany(company);
        productRepository.save(product);
    }
//totalProductForCompany//getProductForCompany
    public Integer totalProductForCompany(Integer id){
        Company company = companyRepository.findCompanyById(id);
        return company.getProducts().size();

    }

    public Set<Product> getProductForCompany(Integer id){
        Company company = companyRepository.findCompanyById(id);
        return company.getProducts();

    }


    //1
    public Set<Product> getAllProductByCompanyId(Integer userId) {
        User user=userRepository.findUserById(userId);

        return user.getCompany().getProducts();
    }

    public Boolean checkAvailabilityProduct(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null)
            throw new ApiException(" product not found");
        if (product.getQuantity()>0)
            return true;
        return false;
    }

    public boolean check(Integer companyId,Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        if (product.getCompany().getId() != companyId)
            throw new ApiException("this product unauthorized for you");
        return true;
    }

   //2
    public Product getProductById(Integer productId, Integer userId) {
        Product product = productRepository.findProductById(productId);
        if (product == null || !product.getCompany().getId().equals(userId))
            throw new ApiException("Product not found or does not belong to the specified Company");

        return productRepository.findProductById(productId);
    }


    public void updateProduct(Integer userId, Integer productId, Product product) {
        Product product1 = productRepository.findProductById(productId);

        if (product1.getCompany().getId()==userId) {

            product1.setName(product.getName());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setQuantity(product.getQuantity());
            product1.setCategory(product.getCategory());
            product1.setFixPrice(product.getFixPrice());
            product1.setPercentOfDefective(product.getPercentOfDefective());
            product1.setIsAppear(product.getIsAppear());

            productRepository.save(product1);
        }
    }



    public void deleteProduct(Integer userId, Integer productId) {
        Product product = productRepository.findProductById(productId);

        if (product.getId()==userId) {

            productRepository.delete(product);
        } else {
            throw new ApiException("Product does not belong to the specified Company");
        }
    }






  //3 ,, search by customer
    public Product getProductByTitle( String name ){
        Product product = productRepository.findProductByName(name);


        return product;
    }


    //4
    public Set<Product> getProductByCompanyName(String companyName) {
        Company company = companyRepository.findCompanyByName(companyName);
        if (company == null)throw new ApiException("Company not found");

        return   company.getProducts();

    }



    //5
    public List<Product> getProductByPercentOfDefective(Integer companyId, Double percentOfDefective) {
        Company company = companyRepository.findCompanyById(companyId);
        List<Product> products = productRepository.findProductByPercentOfDefective(percentOfDefective);

        List<Product> companyProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getCompany().equals(company)) {
                companyProducts.add(product);
            }
        }

        return companyProducts;
    }}






