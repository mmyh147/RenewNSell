package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.DTO_BUY;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public List<OrderProduct> getAll() {
        if (orderRepository.findAll().isEmpty())
            throw new ApiException("Order List is empty");
        else return orderRepository.findAll();
    }

    public void addOrder(OrderProduct orderProduct) {
        orderProduct.setStatus("PENDING");
        orderRepository.save(orderProduct);
    }

    public void updateOrder(Integer id, OrderProduct orderProduct) {
        if (check(id)) {
            OrderProduct orderProduct1 = orderRepository.findOrderProductById(id);
            orderProduct1.setTotalPrice(orderProduct.getTotalPrice());
            orderProduct1.setCustomer(orderProduct.getCustomer());
            orderProduct1.setDate(orderProduct.getDate());//we need this update to test but its prevent to anyone to updated so this line for us
            orderProduct1.setStatus(orderProduct.getStatus());
            orderProduct1.setTotalItems(orderProduct.getTotalItems());
            orderProduct1.setFixProduct(orderProduct.getFixProduct());
            orderProduct1.setTax(orderProduct.getTax());
        }

    }

    public void delete(Integer orderID) {
        if (check(orderID)) {
            OrderProduct orderProduct1 = orderRepository.findOrderProductById(orderID);
            orderRepository.delete(orderProduct1);

        }

    }

    ///Ghaliah
    public void buy(Integer userId, List<DTO_BUY> productIds) {
        Customer customer = customerRepository.findCustomersById(userId);
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setCustomer(customer);
        Double totalPrices = 0.0;
        /////////////////////////////////////////////
        List<Product> products = new ArrayList<>();
        ///
        for (DTO_BUY productId : productIds) {
            if (productRepository.findProductById(productId.getProductId()) == null)
                throw new ApiException("One of product Not found");
            Product product = productRepository.findProductById(productId.getProductId());
            product.setBuyWithFix(productId.isFix());//if customer wants fix product
            if (product.getQuantity()==0){
                throw new ApiException("product name: "+product+" sold out");
            }
            product.setQuantity(product.getQuantity() - 1);//update Quantity}
            productRepository.save(product);//update
            products.add(product);
        }
        /// AssignToOder
        for (Product productAssignToOrder : products) {
            productAssignToOrder.setOrderProduct(orderProduct);
            if (productAssignToOrder.getBuyWithFix()) {
                totalPrices += productAssignToOrder.getPrice() + productAssignToOrder.getFixPrice();
            } else {
                totalPrices += productAssignToOrder.getPrice();
            }
        }
        orderProduct.setTotalItems(products.size());
        totalPrices -= totalPrices * 0.15;
        orderProduct.setTotalPrice(totalPrices);
        orderProduct.setTax(0.15);
        orderProduct.setDate(LocalDate.now());
        addOrder(orderProduct);

    }

    //==========================================+cancelOrder(int):=======================

    public void cancelOrder(Integer customerId, Integer orderId) {
        if (check(orderId)) {
            OrderProduct orderProduct1 = orderRepository.findOrderProductById(orderId);
            if (orderProduct1.getCustomer().getId()==customerId){
            if (!orderProduct1.getStatus().equalsIgnoreCase("DELIVERED")) {
                if (orderProduct1.getStatus().equalsIgnoreCase("REJECTED"))
                    throw new ApiException("your order is Already Rejected");
                if (orderProduct1.getStatus().equalsIgnoreCase("CANCELED"))
                    throw new ApiException("your order is Already Canceled");
                else {
                    orderProduct1.setStatus("CANCELED");
                    orderRepository.save(orderProduct1);
                }}
            else {throw new ApiException("order not for you");}
            }
            throw new ApiException("you can't cancel delivered order");
        }
    }

    //==================================changeStatus==========================
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
            case "REJECT":
                order.setStatus("REJECTED");
                orderRepository.save(order);
                break;
            case "PENDING":
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

        }
    }
//=================================================


    //=====================================//+getAllCanceledOrder():List<Order>
    public List<OrderProduct> getAllByStatus(String status) {
        if (orderRepository.findAllByStatus(status).isEmpty())
            throw new ApiException("Empty List of order with status " + status);
        return orderRepository.findAllByStatus(status);
    }
//==========================getStatusOfFixProduct
public String getStatusOfOrder(Integer customerId, Integer orderId) {
    OrderProduct orderProduct = orderRepository.findOrderProductById(orderId);
    if (orderProduct == null)
    {throw new ApiException("order don't found");}
    else if (orderProduct.getCustomer().getId()!=customerId)
        throw new ApiException("this order not unauthorized for you");
    return orderProduct.getStatus();
}

    //==========================================================================
    public Boolean check(Integer orderId) {
        OrderProduct orderProduct1 = orderRepository.findOrderProductById(orderId);
        if (orderProduct1 == null) {
            throw new ApiException("Not Found Order");
        }
        return true;
    }


}
