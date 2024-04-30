package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {
}
