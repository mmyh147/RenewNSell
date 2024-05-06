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

@Service@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final OrderCompanyRepository orderCompanyRepository;

    public List<Product> getAll() {
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
    //=================================checkAvailabilityProduct DONE BY GHALIAH  ==============================

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












    //============================================
}
