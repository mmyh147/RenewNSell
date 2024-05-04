package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.Product;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct, Integer> {

    OrderProduct findOrderProductById(Integer id);
    List<OrderProduct> findAllByStatus(String status);
}
