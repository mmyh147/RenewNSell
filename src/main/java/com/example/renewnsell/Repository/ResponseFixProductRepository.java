package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.ResponseFixProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseFixProductRepository extends JpaRepository<ResponseFixProduct, Integer> {
    ResponseFixProduct findResponseFixProductById(Integer id);
}
