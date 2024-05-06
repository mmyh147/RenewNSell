package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(Integer id);
    List<Product> findProductsByCompanyIdAndPercentOfDefective(Integer companyId,Double precent);

}
