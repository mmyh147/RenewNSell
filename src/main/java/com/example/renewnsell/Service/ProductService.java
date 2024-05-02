package com.example.renewnsell.Service;

import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.ProductRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
private final CompanyRepository companyRepository;
private final UserRepository userRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public void addProduct(Integer userId, Product product) {
        User user = userRepository.findUserById(userId);
        Company company = companyRepository.findCompanyById(user.getId());
        product.setCompany(company);
        productRepository.save(product);      }















    //============================================
}
