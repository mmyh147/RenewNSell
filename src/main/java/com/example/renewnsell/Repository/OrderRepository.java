package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.Product;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct, Integer> {

    OrderProduct findOrderProductById(Integer id);
    List<OrderProduct> findAllByStatus(String status);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCELED'")
    double getTotalProfitForNonCancelledOrders();

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCELED' AND op.company.id = :companyId")
    double getTotalProfitForNonCancelledOrdersByCompanyId(Integer companyId);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCELED' AND op.date = :todayDate")
    double getTotalProfitForNonCancelledOrdersToday(LocalDate todayDate);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCELED' AND op.date = :todayDate AND op.company.id = :companyId")
    double getTodayProfitForNonCancelledOrdersByCompanyId(LocalDate todayDate, Integer companyId);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCELED' AND YEAR(op.date) = YEAR(:currentDate) AND MONTH(op.date) = MONTH(:currentDate) AND op.company.id = :companyId")
    double getCurrentMonthProfitForNonCancelledOrdersByCompanyId(LocalDate currentDate, Integer companyId);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE YEAR(op.date) = YEAR(CURRENT_DATE) AND MONTH(op.date) = MONTH(CURRENT_DATE)")
    double getCurrentMonthProfitForAllOrders();


    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCEL' AND YEAR(op.date) = YEAR(:lastMonth) AND MONTH(op.date) = MONTH(:lastMonth) AND op.company.id = :companyId")
    double getLastMonthProfitForNonCancelledOrdersByCompanyId(LocalDate lastMonth, Integer companyId);

    @Query("SELECT COALESCE(SUM(op.totalPrice), 0) FROM OrderProduct op WHERE op.status <> 'CANCEL' AND YEAR(op.date) = YEAR(:lastMonth) AND MONTH(op.date) = MONTH(:lastMonth)")
    double getLastMonthProfitForNonCancelledOrders(LocalDate lastMonth);
}
