package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.OrderCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderCompanyRepository extends JpaRepository<OrderCompany,Integer> {
    OrderCompany findOrderCompanyById(Integer id);

    @Query("SELECT SUM(o.totalPrice) FROM Company c JOIN c.orders o WHERE c.id = :companyId AND o.status = 'DELIVERED'")
    Double findTotalProfitForCompany( Integer companyId);

    @Query("SELECT SUM(o.totalPrice) FROM Company c JOIN c.orders o WHERE c.id = :companyId AND o.status = 'DELIVERED' AND o.date = :today")
    Double findTotalProfitForCompanyToday(@Param("companyId") Integer companyId,  LocalDate today);

    @Query("SELECT SUM(o.totalPrice) FROM Company c JOIN c.orders o WHERE c.id = :companyId AND o.status = 'DELIVERED' AND YEAR(o.date) = YEAR(:currentDate) AND MONTH(o.date) = MONTH(:currentDate)")
    Double findTotalProfitForCompanyCurrentMonth( Integer companyId, LocalDate currentDate);

    @Query("SELECT SUM(o.totalPrice) FROM Company c JOIN c.orders o WHERE c.id = :companyId AND o.status = 'DELIVERED' AND YEAR(o.date) = :year AND MONTH(o.date) = :month")
    Double findTotalProfitForCompanyLastMonth(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT SUM(o.totalPrice) FROM OrderCompany o WHERE o.status = 'DELIVERED' AND o.date = :today")
    Double findTotalProfitForToday(@Param("today") LocalDate today);

    @Query("SELECT SUM(o.totalPrice) FROM OrderCompany o WHERE o.status = 'DELIVERED' AND YEAR(o.date) = YEAR(:currentDate) AND MONTH(o.date) = MONTH(:currentDate)")
    Double findTotalProfitForCurrentMonth(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT SUM(o.totalPrice) FROM OrderCompany o WHERE o.status = 'DELIVERED' AND YEAR(o.date) = :year AND MONTH(o.date) = :month")
    Double findTotalProfitForLastMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT COUNT(p) FROM OrderCompany o JOIN o.products p WHERE o.status = 'DELIVERED' AND o.company.id = :companyId")
    Integer countAllProductsSoldForCompany(@Param("companyId") Integer companyId);

    @Query("SELECT COUNT(p) FROM OrderCompany o JOIN o.products p WHERE o.status = 'DELIVERED' AND o.company.id = :companyId AND DATE(o.date) = CURRENT_DATE")
    Integer countProductsSoldTodayForCompany(@Param("companyId") Integer companyId);

    @Query("SELECT COUNT(p) FROM OrderCompany o JOIN o.products p WHERE o.status = 'DELIVERED' AND o.company.id = :companyId AND YEAR(o.date) = YEAR(CURRENT_DATE) AND MONTH(o.date) = MONTH(CURRENT_DATE)")
    Integer countProductsSoldCurrentMonthForCompany(@Param("companyId") Integer companyId);

//    @Query("SELECT COUNT(p) FROM OrderCompany o JOIN o.products p WHERE o.status = 'DELIVERED' AND o.company.id = :companyId AND FUNCTION('YEAR', o.date) = FUNCTION('YEAR', CURRENT_DATE - 1) AND FUNCTION('MONTH', o.date) = FUNCTION('MONTH', CURRENT_DATE - 1)")
//    Integer countProductsSoldLastMonthForCompany(@Param("companyId") Integer companyId);
}
