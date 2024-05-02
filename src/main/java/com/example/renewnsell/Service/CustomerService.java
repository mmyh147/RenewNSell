package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.CustomerDTO;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CustomerRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {



    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


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

    public void update(Integer customerId, CustomerDTO updatedCustomer){


        User user = userRepository.findUserById(customerId);
        if (user.getCustomer() == null){
            throw new ApiException("customer not found");
        }
        user.setName(updatedCustomer.getName());
        user.setEmail(updatedCustomer.getEmail());
        user.setUsername(updatedCustomer.getUsername());
        user.setPassword(updatedCustomer.getPassword());
        user.getCustomer().setGender(updatedCustomer.getGender());


        userRepository.save(user);
        customerRepository.save(user.getCustomer());

    }


    public Customer getCustomerByID(Integer id){

        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null){
            throw new ApiException("Customer not found with ID : " + id);
        }
        return customer;
    }
}
