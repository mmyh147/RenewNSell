package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.ProductRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void addProduct(Integer userId, Product product) {
        User user = userRepository.findUserById(userId);
        Company company = companyRepository.findCompanyById(user.getId());
        product.setCompany(company);
        productRepository.save(product);
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
//    public List<Product> findAllByCompanyIdAndAndBuyWithFix(Integer companyId) {
//        List<Product> productList = productRepository.findAllByCompany_IdAndAndBuyWithFixOrBuyWithFix(companyId, true, false);
//        if (productList.isEmpty())
//            throw new ApiException("sold productList is empty");
//        List<OrderProduct> orderProductList = new ArrayList<>();
//        for (Product product : productList) {
//            if (product.getOrderProduct() != null) {
//                orderProductList.add(product.getOrderProduct());
//            }
//        }
//        if (orderProductList.isEmpty())
//            throw new ApiException("Company Dont have Order yet");
//        return productList;
//    }













    //============================================
}
