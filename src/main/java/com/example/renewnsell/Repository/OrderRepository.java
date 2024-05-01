package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct, Integer> {
    OrderProduct findAllByCustomer_Id(Integer id);
}
