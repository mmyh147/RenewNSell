package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct, Integer> {
    OrderProduct findAllByCustomer_Id(Integer id);
    OrderProduct findOrderProductById(Integer id);
    OrderProduct findOrderProductByFixProduct(FixProduct product);
    List<OrderProduct> findAllByStatus(String status);
}
