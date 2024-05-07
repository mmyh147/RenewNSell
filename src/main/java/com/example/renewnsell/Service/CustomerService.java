package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CustomerRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {



    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    public void register(CustomerDTO request){

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

        Customer customer = new Customer();
        customer.setGender(request.getGender());
        customer.setUser(user);


        customerRepository.save(customer);

    }

    public Integer totalCustomers(){
        return customerRepository.findAll().size();
    }

    public Integer totalCustomersByGender(String gender){
        return customerRepository.findAllByGender( gender).size();
    }

}
