package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.FixProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixProductRepository extends JpaRepository<FixProduct, Integer> {
}
