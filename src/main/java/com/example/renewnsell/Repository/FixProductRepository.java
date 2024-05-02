package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixProductRepository extends JpaRepository<FixProduct, Integer> {
    FixProduct findFixProductById(Integer id);
    FixProduct findFixProductsByCustomer_Id(Integer id);
    FixProduct findFixProductsByOrderProduct(OrderProduct orderProduct);
}
