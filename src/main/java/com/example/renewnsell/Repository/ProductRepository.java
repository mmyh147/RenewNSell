package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllProductByCompanyId(Integer company);


    Product findProductById(Integer id);

    Product findProductByName(String name);

    List<Product>  findProductByCategory(String category);


    List<Product>findProductByPercentOfDefective(Double percentOfDefective);

}
