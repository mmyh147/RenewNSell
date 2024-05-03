package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.FixProductDTO;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
//-----------------------------------Ghaliah----------------------------------

@Service
@RequiredArgsConstructor
public class FixProductService {

    private final FixProductRepository fixProductRepository;
    private final CustomerRepository customerRepository;
    private final ResponseFixProductRepository responseFixProductRepository;
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderService orderService;
    //-----------------------------------Ghaliah----------------------------------

    public List<FixProduct> getAll() {
        if (fixProductRepository.findAll().isEmpty()) {
            throw new ApiException("Fix Product List is empty");
        } else return fixProductRepository.findAll();
    }

    //-----------------------------------Ghaliah----------------------------------
    public void add(Integer customerID, FixProductDTO fixProductDTO) {
        Customer customer = customerRepository.findCustomerById(customerID);
        FixProduct fixProduct = new FixProduct();
        fixProduct.setOrderProduct(null);
        fixProduct.setCategory(fixProductDTO.getCategory());
        fixProduct.setCustomer(customer);
        fixProduct.setDescription(fixProductDTO.getDescription());
        fixProduct.setStatus("WAITING");
        fixProductRepository.save(fixProduct);
    }

    public void addFixProduct(Integer customerID, FixProductDTO fixProductDTO) {
        Customer customer = customerRepository.findCustomerById(customerID);
        if (customer == null) {
            throw new ApiException("Please Register to Request Fix Product");
        } else {
            ResponseFixProduct responseFixProduct = new ResponseFixProduct();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setTotalItems(1);
            orderProduct.setDate(LocalDate.now());
            orderProduct.setStatus("WAITING");
            orderProduct.setCustomer(customer);//assign to customer
            FixProduct fixProduct = new FixProduct(null, fixProductDTO.getDescription(), "WAITING", fixProductDTO.getCategory(), orderProduct, customer, responseFixProduct);
            responseFixProduct.setFixProduct(fixProduct);
            //assign to customer and to order
            orderRepository.save(orderProduct);
            fixProductRepository.save(fixProduct);
        }
    }

    public void update(Integer customerId, Integer fixId, FixProductDTO fixProductDTO) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixId);
        FixProduct fixProduct = fixProductRepository.findFixProductsByOrderProduct(orderProduct);
        //
        if (orderProduct == null) {
            throw new ApiException("you don't order");
        } else if (orderProduct.getCustomer().getId() != customerId)
            throw new ApiException("this order not unauthorized for you");
        //
        if (fixProduct.getStatus().equalsIgnoreCase("WAITING")) {
            fixProduct.setCategory(fixProductDTO.getCategory());
            fixProduct.setDescription(fixProduct.getDescription());
            fixProductRepository.save(fixProduct);
        }
        throw new ApiException("we checking your product so cant update fix request WAITING");

    }

    public void delete( Integer fixProductId){
        OrderProduct orderProduct=orderRepository.findOrderProductById(fixProductId);
        if (orderProduct == null)
        {throw new ApiException("you don't order");}
        orderRepository.delete(orderProduct);

    }

    //-----------------------------------Ghaliah----------------------------------


    public void acceptPriceFixProduct(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        FixProduct fixProduct = fixProductRepository.findFixProductsByOrderProduct(orderProduct);
        //
        if (orderProduct == null) {
            throw new ApiException("you don't order");
        } else if (orderProduct.getCustomer().getId() != customerId)
            throw new ApiException("this order not unauthorized for you");
        //
        if (!fixProduct.getStatus().equalsIgnoreCase("WAITING")) {
            throw new ApiException("NOT WAITING");
        }
        orderProduct.setStatus("ACCEPTED");
        fixProduct.setStatus("ACCEPTED");
        orderRepository.save(orderProduct);
        fixProductRepository.save(fixProduct);
        orderService.changeStatus(fixProductId);
    }


    public void rejectPriceFixProduct(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        FixProduct fixProduct = fixProductRepository.findFixProductsByOrderProduct(orderProduct);
        //
        if (orderProduct == null) {
            throw new ApiException("you don't order");
        } else if (orderProduct.getCustomer().getId() != customerId)
            throw new ApiException("this order not unauthorized for you");
        //

        if (!fixProduct.getStatus().equalsIgnoreCase("WAITING")) {
            throw new ApiException("NOT WAITING");
        }
        orderProduct.setStatus("REJECT");
        fixProduct.setStatus("REJECT");
        orderRepository.save(orderProduct);
        fixProductRepository.save(fixProduct);
        orderService.changeStatus(fixProductId);

    }

    //-----------------------------------Ghaliah----------------------------------


    //-----------------------------------Ghaliah----------------------------------


    public FixProduct getFixProductOne(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        if (orderProduct == null) {
            throw new ApiException("you don't order");
        } else if (orderProduct.getCustomer().getId() != customerId)
            throw new ApiException("this order not unauthorized for you");

        return fixProductRepository.findFixProductById(orderProduct.getId());
    }




}
