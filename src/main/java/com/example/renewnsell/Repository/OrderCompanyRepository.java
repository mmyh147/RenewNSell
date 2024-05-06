package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.OrderCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCompanyRepository extends JpaRepository<OrderCompany,Integer> {
    OrderCompany findOrderCompanyById(Integer id);

}
