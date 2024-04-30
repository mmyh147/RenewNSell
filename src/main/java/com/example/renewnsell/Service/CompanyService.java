package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.CompanyDTO;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {




    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    public void register(CompanyDTO request){

        if (userRepository.findUserByUsername(request.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }


        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNumber());

        user.setRole("CUSTOMER");
        userRepository.save(user);

        Company company = new Company();
        company.setCommercialLicense(request.getCommercialLicense());
        company.setIndustry(request.getIndustry());
        company.setLogoPath(request.getLogoPath());
        company.setUser(user);


        companyRepository.save(company);

    }
}
