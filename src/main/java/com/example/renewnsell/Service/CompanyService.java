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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {




    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public List<Company> getAll() {
        if (companyRepository.findAll().isEmpty()){
            throw new ApiException("no Companies found");
        }
        return companyRepository.findAll();
    }


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

        user.setRole("COMPANY");
        userRepository.save(user);

        Company company = new Company();
        company.setCommercialLicense(request.getCommercialLicense());
        company.setIndustry(request.getIndustry());
        company.setLogoPath(request.getLogoPath());
        company.setUser(user);


        companyRepository.save(company);

    }

    public void update(Integer companyId, CompanyDTO updatedCompanyDTO){


        User user = userRepository.findUserById(companyId);

        if (user.getCompany() == null){
            throw new ApiException("company not found");
        }

        user.setName(updatedCompanyDTO.getName());
        user.setEmail(updatedCompanyDTO.getEmail());
        user.setUsername(updatedCompanyDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(updatedCompanyDTO.getPassword()));
        user.getCompany().setCommercialLicense(updatedCompanyDTO.getCommercialLicense());
        user.getCompany().setIndustry(updatedCompanyDTO.getIndustry());
        user.getCompany().setLogoPath(updatedCompanyDTO.getLogoPath());


        userRepository.save(user);
        companyRepository.save(user.getCompany());

    }

    public void updateMyUser(User user, CompanyDTO updatedCompanyDTO){


//        User user = userRepository.findUserById(companyId);

//        if (user.getCompany() == null){
//            throw new ApiException("company not found");
//        }

        user.setName(updatedCompanyDTO.getName());
        user.setEmail(updatedCompanyDTO.getEmail());
        user.setUsername(updatedCompanyDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(updatedCompanyDTO.getPassword()));
        user.getCompany().setCommercialLicense(updatedCompanyDTO.getCommercialLicense());
        user.getCompany().setIndustry(updatedCompanyDTO.getIndustry());
        user.getCompany().setLogoPath(updatedCompanyDTO.getLogoPath());


        userRepository.save(user);
        companyRepository.save(user.getCompany());

    }

    public User getCompanyById(Integer id){

        User user = userRepository.findUserById(id);
        if (user.getCompany() == null){
            throw new ApiException("company not found with ID : " + id);
        }
        return user;
    }

}
