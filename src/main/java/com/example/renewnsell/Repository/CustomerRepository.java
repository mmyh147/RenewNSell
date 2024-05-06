package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomersById(Integer id);
    List<Customer> findAllByGender(String gender);
}
