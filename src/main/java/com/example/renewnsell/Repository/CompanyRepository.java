package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(Integer id);

@Query("select c from Company c where c.user.name=?1")
Company findCompanyByName(String name);


}
