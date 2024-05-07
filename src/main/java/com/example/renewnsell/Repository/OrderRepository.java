package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.Product;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct, Integer> {

    OrderProduct findOrderProductById(Integer id);
    List<OrderProduct> findAllByStatus(String status);

    @Query("SELECT o FROM OrderProduct o WHERE o.date = CURRENT_DATE AND o.status = 'DELIVERED' ORDER BY o.status")
    List<OrderProduct> findAllByStatusAndDate();

    @Query("SELECT o FROM OrderProduct o WHERE FUNCTION('MONTH', o.date) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', o.date) = FUNCTION('YEAR', CURRENT_DATE) AND o.status = 'DELIVERED' ORDER BY o.status")
    List<OrderProduct> findOrdersCurrentMonth();

    @Query("SELECT o FROM OrderProduct o WHERE  YEAR(o.date) = :year AND MONTH(o.date) = :month AND o.status = 'DELIVERED' ORDER BY o.status")
    List<OrderProduct> findDeliveredOrdersLastMonth(@Param("year") Integer year, @Param("month") Integer month);




}
