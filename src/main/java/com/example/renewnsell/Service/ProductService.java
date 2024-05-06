package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CompanyRepository;
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


    public void addProduct(Integer userId, Product product) {
        User user = userRepository.findUserById(userId);
        Company company = companyRepository.findCompanyById(user.getId());
        product.setCompany(company);
        productRepository.save(product);

    }


    //1
    public Set<Product> getAllProductByCompanyId(Integer userId) {
        User user = userRepository.findUserById(userId);

        return user.getCompany().getProducts();
    }


    //2
    public Product getProductById(Integer productId, Integer userId) {
        Product product = productRepository.findProductById(productId);
        if (product == null || !product.getCompany().getId().equals(userId)) {
            throw new ApiException("Product not found or does not belong to the specified Company");
        }
        return product;
    }


    public void updateProduct(Integer userId, Integer productId, Product product) {
        Product product1 = productRepository.findProductById(productId);

        if(product1==null)throw new ApiException("Product not found or does not belong to the specified Company");
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

        if (product.getCompany().getId()==userId) {

            productRepository.delete(product);
        } else {
            throw new ApiException("Product does not belong to the specified Company");
        }
    }






  //3
    public Product getProductByName( String name ) {
        Product product = productRepository.findProductByName(name);
        if (product == null) throw new ApiException("Product not found or does not belong to the specified Company");

        return product;
    }


    //4
    public Set<Product> getProductByCompanyName(String name) {
        Company company = companyRepository.findCompanyByName(name);
        if (company == null)throw new ApiException("Company not found");

        return company.getProducts();

    }





    //5
    public List<Product> getProductByPercentOfDefective( Double percentOfDefective) {
        List<Product> products = productRepository.findProductByPercentOfDefective(percentOfDefective);


        if(products.isEmpty())throw new ApiException("No products found");

        return products;
    }


    //6

    public List<Product> getProductByCategory( String category ){
        List<Product> p = productRepository.findProductByCategory(category);

        if(p.isEmpty())throw new ApiException("No products found");

        return p;
    }


    //7
    public List<Product> getAllProduct() {

        if (productRepository.findAll().isEmpty())throw new ApiException("No products found");
        return productRepository.findAll();
    }



}






