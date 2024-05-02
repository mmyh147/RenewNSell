package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.FixProductDTO;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.CustomerRepository;
import com.example.renewnsell.Repository.FixProductRepository;
import com.example.renewnsell.Repository.OrderRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
//-----------------------------------Ghaliah----------------------------------

@Service
@RequiredArgsConstructor
public class FixProductService {

    private final FixProductRepository fixProductRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    //-----------------------------------Ghaliah----------------------------------

    public List<FixProduct> getAll() {
        if (fixProductRepository.findAll().isEmpty()) {
            throw new ApiException("Fix Product List is empty");
        } else return fixProductRepository.findAll();
    }

    //-----------------------------------Ghaliah----------------------------------
    public void add(Integer customerID, FixProductDTO fixProductDTO){
        Customer customer=customerRepository.findCustomersById(customerID);
        FixProduct fixProduct=new FixProduct();
       fixProduct.setOrderProduct(null);
       fixProduct.setCategory(fixProductDTO.getCategory());
       fixProduct.setCustomer(customer);
       fixProduct.setDescription(fixProductDTO.getDescription());
       fixProduct.setStatus("WAITING");
       fixProductRepository.save(fixProduct);
    }
    public void addFixProduct(Integer customerID, FixProductDTO fixProductDTO) {
        Customer customer = customerRepository.findCustomersById(customerID);
        if (customer == null) {
            throw new ApiException("Please Register to Request Fix Product");
        } else {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setTotalItems(1);
            orderProduct.setDate(LocalDate.now());
            orderProduct.setStatus("WAITING");
            orderProduct.setCustomer(customer);//assign to customer
            FixProduct fixProduct = new FixProduct(null, fixProductDTO.getDescription(), "WAITING", fixProductDTO.getCategory(), orderProduct, customer);
            //assign to customer and to order
            orderRepository.save(orderProduct);
            fixProductRepository.save(fixProduct);
        }
    }

    //-----------------------------------Ghaliah----------------------------------
    public void response(Integer fixProductId, Double price, String description) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        FixProduct fixProduct = fixProductRepository.findFixProductsByOrderProduct(orderProduct);
        if (fixProduct == null) {
            throw new ApiException("FixProduct not found");
        }
        String stringPrice = Double.toString(price);
        fixProduct.setDescription(description + " " + stringPrice);
        orderProduct.setTotalItems(1);
        orderProduct.setTotalPrice(price);
        orderRepository.save(orderProduct);
        fixProductRepository.save(fixProduct);

    }

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
        changeStatus(fixProductId);
    }


    public void rejectPriceFixProduct(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        FixProduct fixProduct = fixProductRepository.findFixProductsByOrderProduct(orderProduct);
        //
        if (orderProduct==null)
        {throw new ApiException("you don't order");}
        else if (orderProduct.getCustomer().getId()!=customerId)
            throw new ApiException("this order not unauthorized for you");
        //

        if (!fixProduct.getStatus().equalsIgnoreCase("WAITING")) {
            throw new ApiException("NOT WAITING");}
            orderProduct.setStatus("REJECT");
            fixProduct.setStatus("REJECT");
            orderRepository.save(orderProduct);
            fixProductRepository.save(fixProduct);
            changeStatus(fixProductId);

    }

    //-----------------------------------Ghaliah----------------------------------

    public void changeStatus(Integer fixProductId) {
        OrderProduct order = orderRepository.findOrderProductById(fixProductId);
        if (order == null) {
            throw new ApiException("Order Not Found ");
        }
        //"PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY"
        switch (order.getStatus()) {
            case "ACCEPTED":
                order.setStatus("PENDING");
                orderRepository.save(order);
                break;
            case "PENDING":
                //call method Accept
                order.setStatus("ORDER_CONFIRMED");
                orderRepository.save(order);
                break;
            case "ORDER_CONFIRMED":
                order.setStatus("PREPARING");
                orderRepository.save(order);
                break;
            case "PREPARING":
                order.setStatus("SHIPPED");
                orderRepository.save(order);
                break;

            case "SHIPPED":
                order.setStatus("OUT_OF_DELIVERY");
                orderRepository.save(order);
                break;
            case "OUT_OF_DELIVERY":
                order.setStatus("DELIVERED");
                orderRepository.save(order);
                break;
            case "REJECT":
                FixProduct product = fixProductRepository.findFixProductsByOrderProduct(order);
                order.setStatus("REJECTED");
                orderRepository.save(order);
                break;
        }
    }
    //-----------------------------------Ghaliah----------------------------------

    public String getStatusOfFixProduct(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        if (orderProduct == null)
        {throw new ApiException("you don't order");}
       else if (orderProduct.getCustomer().getId()!=customerId)
            throw new ApiException("this order not unauthorized for you");
        return orderProduct.getStatus();
    }

    public FixProduct getFixProductOne(Integer customerId, Integer fixProductId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
        if (orderProduct == null)
        {throw new ApiException("you don't order");}
        else if (orderProduct.getCustomer().getId()!=customerId)
            throw new ApiException("this order not unauthorized for you");

        return fixProductRepository.findFixProductById(orderProduct.getId());
    }


    public void delete( Integer fixProductId){
        OrderProduct orderProduct=orderRepository.findOrderProductById(fixProductId);
        if (orderProduct == null)
        {throw new ApiException("you don't order");}
        orderRepository.delete(orderProduct);

    }


}
