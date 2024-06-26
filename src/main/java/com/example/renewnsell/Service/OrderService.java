package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.DTO_BUY;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderCompanyRepository orderCompanyRepository;
    @Autowired
    private final OrderCompanyService orderCompanyService;

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
//================================= ALL CRUD DONE BY GHALIAH  ==============================


    //================================= [BUY] METHOD DONE BY GHALIAH  ==============================
    public void buy(Integer userId, List<DTO_BUY> productIds) {
        /*we have 3 relation with OrderProduct
        * First relation is ManyToMany between Order product and Product
        * Second relation is OneToMany between OrderProduct and OrderCompany // this relation I use to divide
        * each product to its company as order , so The benefit is [ distribution ]
        * Third relations is OneToMany between OrderProduct and Customer
        * */

        Customer customer = customerRepository.findCustomersById(userId);
        OrderProduct orderProduct = new OrderProduct();
        OrderCompany orderCompany = new OrderCompany();
        orderCompany.setOrderProduct(orderProduct);
        orderCompany.setStatus("PENDING");
        orderCompany.setDate(LocalDate.now());
        orderProduct.setCustomer(customer);
        Double totalPrices = 0.0;
        /////////////////////////////////////////////
        List<Product> products = new ArrayList<>();
        Set<Product> productSet = new HashSet<Product>(products);//Assign product set to order

        //=======
        List<Product> productOrderCompany = new ArrayList<>();
        Set<Product> orderCompanySet = new HashSet<Product>(productOrderCompany);//Assign product set to order

        //=====

        orderRepository.save(orderProduct);//update //save order first then save updated product
        orderCompany.setProducts(orderCompanySet);
        orderCompanyRepository.save(orderCompany);

        ///
        for (DTO_BUY productId : productIds) {
            if (productRepository.findProductById(productId.getProductId()) == null)
                throw new ApiException("One of product Not found");
            Product product = productRepository.findProductById(productId.getProductId());
            orderCompany.setBuyWithFix(productId.isFix());//if customer wants fix product
            if (product.getQuantity() == 0) {
                throw new ApiException("product name: " + product + " sold out");
            }
            product.setQuantity(product.getQuantity() - 1);//update Quantity}
            product.getOrderProduct().add(orderProduct);//orderWebsite assign to product
            orderCompany.setCompany(product.getCompany());
            product.getOrderCompany().add(orderCompany);//orderCompany assign to product
            orderCompany.getProducts().add(product);
            orderCompany.setDate(LocalDate.now());
            orderCompany.setStatus("PENDING");
            if (productId.isFix()) {
                orderCompany.setTotalPrice(product.getPrice() + product.getFixPrice());
            } else {
                orderCompany.setTotalPrice(product.getPrice());
            }
            productRepository.save(product);//update
            orderCompanyRepository.save(orderCompany);
            products.add(product);
        }
        orderProduct.setProducts(productSet);

        orderRepository.save(orderProduct);//update

        for (DTO_BUY p : productIds) {
            if (p.isFix()) {
                totalPrices += productRepository.findProductById(p.getProductId()).getPrice() + productRepository.findProductById(p.getProductId()).getFixPrice();

            } else {
                totalPrices += productRepository.findProductById(p.getProductId()).getPrice();
            }
        }
        orderProduct.setTotalItems(products.size());
        totalPrices += totalPrices * 0.15;
        orderProduct.setTotalPrice(totalPrices);
        orderProduct.setTax(0.15);
        orderProduct.setDate(LocalDate.now());
        addOrder(orderProduct);
//======================CREATE ORDER FOR EACH COMPANY OF PRODUCT LIST =====================================


    }

//================================= [CANCEL ORDER] METHOD DONE BY GHALIAH  ==============================

    public void cancelOrder(Integer customerId, Integer orderId) {
        if (check(orderId)) {
            OrderProduct orderProduct1 = orderRepository.findOrderProductById(orderId);
            if (orderProduct1.getCustomer().getId() == customerId) {
                if (orderProduct1.getStatus().equalsIgnoreCase("REJECTED")) {
                    throw new ApiException("your order is Already Rejected");
                }
                if (orderProduct1.getStatus().equalsIgnoreCase("CANCELED")) {
                    throw new ApiException("your order is Already Canceled");
                }

                if (!orderProduct1.getStatus().equalsIgnoreCase("DELIVERED")) {
                    orderProduct1.setStatus("CANCELED");
                    for (OrderCompany orderCompany : orderProduct1.getOrderCompanySet()) {
                        orderCompany.setStatus("CANCELED");// tell all companies in this Product order the customer cancel his/her order
                        orderCompanyRepository.save(orderCompany);
                    }
                    orderRepository.save(orderProduct1);
                } else {
                    throw new ApiException("you can't cancel delivered order");
                }

            } else {
                throw new ApiException("order not for you");
            }
        }
    }

    //================================= [CHANGE STATUS] METHOD DONE BY GHALIAH  ==============================
    public void changeStatus(Integer orderId) {
        OrderProduct order = orderRepository.findOrderProductById(orderId);
        if (order == null) {
            throw new ApiException("Order Not Found ");
        }
        if (order.getStatus().equalsIgnoreCase("REJECT"))
            throw new ApiException("you can't change rejected order");
        if (order.getStatus().equalsIgnoreCase("CANCELED"))
            throw new ApiException("you can't change canceled order");
        if (order.getStatus().equalsIgnoreCase("DELIVERED"))
            throw new ApiException("order already is delivered");
        //"PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_FOR_DELIVERY"
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
                for (OrderCompany orderCompany : order.getOrderCompanySet()) {
                    if (!orderCompany.getStatus().equalsIgnoreCase("PENDING")) {
                        order.setStatus("ORDER_CONFIRMED");
                        orderRepository.save(order);
                    } else throw new ApiException("some of product of some company not  still PENDING");
                }
                break;
            case "ORDER_CONFIRMED":
                for (OrderCompany orderCompany : order.getOrderCompanySet()) {
                    if (!orderCompany.getStatus().equalsIgnoreCase("ORDER_CONFIRMED")) {
                        if (!orderCompany.getStatus().equalsIgnoreCase("PENDING")) {
                            order.setStatus("PREPARING");
                            orderRepository.save(order);
                        } else {
                            throw new ApiException("some of product of some company not  still PENDING");
                        }
                    } else throw new ApiException("some of product of some company not  still PREPARING");
                }
                break;
            case "PREPARING":
                for (OrderCompany orderCompany : order.getOrderCompanySet()) {
                    if (!orderCompany.getStatus().equalsIgnoreCase("PREPARING")) {
                        if (!orderCompany.getStatus().equalsIgnoreCase("PENDING")) {
                            order.setStatus("SHIPPED");
                            orderRepository.save(order);
                        } else {
                            throw new ApiException("some of product of some company not  still PENDING");
                        }
                    } else throw new ApiException("some of product of some company not  still SHIPPED");
                }
                break;
            case "SHIPPED":
                for (OrderCompany orderCompany : order.getOrderCompanySet()) {
                    if (!orderCompany.getStatus().equalsIgnoreCase("SHIPPED")) {
                        if (!orderCompany.getStatus().equalsIgnoreCase("PENDING")) {
                            order.setStatus("OUT_FOR_DELIVERY");
                            orderRepository.save(order);
                        } else {
                            throw new ApiException("some of product of some company not  still PENDING");
                        }
                    } else throw new ApiException("some of product of some company not  still OUT_FOR_DELIVERY");
                }
                break;
            case "OUT_FOR_DELIVERY":
                for (OrderCompany orderCompany : order.getOrderCompanySet()) {
                    if (orderCompany.getStatus().equalsIgnoreCase("DELIVERED")) {
                        order.setStatus("DELIVERED");
                        orderRepository.save(order);
                    } else throw new ApiException("some of product of some company not  still DELIVERED");
                }
                break;


        }
    }
//=================================================


    //================================= [GET ALL ORDER BY STATUS] METHOD DONE BY GHALIAH  ==============================
    public List<OrderProduct> getAllByStatus(String status) {
        if (orderRepository.findAllByStatus(status).isEmpty())
            throw new ApiException("Empty List of order with status " + status);
        return orderRepository.findAllByStatus(status);
    }

    //================================= [GET STATUS OF ORDER ] METHOD DONE BY GHALIAH  ==============================
    public String track(Integer customerId, Integer orderId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(orderId);
        if (orderProduct == null) {
            throw new ApiException("order don't found");
        } else if (orderProduct.getCustomer().getId()!= customerId)
            throw new ApiException("this order unauthorized for you");
        return orderProduct.getStatus();
    }
    //================================= [TRUCK ORDER FOR EMPLOYEE METHOD ] METHOD DONE BY GHALIAH  ==============================
    public String track(Integer orderId) {
        OrderProduct orderProduct = orderRepository.findOrderProductById(orderId);
        if (orderProduct == null) {
            throw new ApiException("order don't found");
        }
            return orderProduct.getStatus();

    }
    //================================= [findAllByCustomer_Id  ] METHOD DONE BY GHALIAH  ==============================

    public Set<OrderProduct> findAllByCustomer_Id(Integer customerId) {
        Customer customer = customerRepository.findCustomersById(customerId);
        if (customer.getOrders().isEmpty())
            throw new ApiException("you dont have order");
        return customer.getOrders();
    }

    //================================= [findAllByCompanyId  ] METHOD DONE BY GHALIAH  ==============================


    //================================= [+getOrdersByProductId:list<Order>  ] METHOD NOT-DONE   ==============================
    public Set<OrderProduct> getAllOrderByProductId(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null)
            throw new ApiException("product not found");
        if (product.getOrderProduct().isEmpty())
            throw new ApiException("Product dont have order yet");
        return product.getOrderProduct();
    }
    //================================= [totalOrders  ] METHOD DONE BY HAYA  ==============================
//+getTotalNumberOfOrders():Int

    public Integer totalOrders() {
        return getAll().size();
    }

    //================================= [THIS METHOD USE IT TO REMOVE DUPLICATION CODE IN EACH METHOD WE SHOULD CHECK AVAILABILITY OF ORDER ] METHOD DONE BY GHALIAH  ==============================
    public Boolean check(Integer orderId) {
        OrderProduct orderProduct1 = orderRepository.findOrderProductById(orderId);
        if (orderProduct1 == null) {
            throw new ApiException("Not Found Order");
        }
        return true;
    }

    // =============================== calculate total profit ========================

    public Double getAllRevenueForWebsite(){

        Double sum = 0.0;
        List<OrderProduct> orderProducts = orderRepository.findAllByStatus("DELIVERED");
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                for (OrderCompany orderCompany : product.getOrderCompanySet() ){
                    sum += product.getTotalPrice() - orderCompany.getTotalPrice();

                }
            }else {
                sum += product.getTotalPrice();
            }
        }
        return sum;
    }

    public Double getTodayRevenueForWebsite(){

        Double sum = 0.0;
        List<OrderProduct> orderProducts = orderRepository.findAllByStatusAndDate();
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                for (OrderCompany orderCompany : product.getOrderCompanySet() ){
                    sum += product.getTotalPrice() - orderCompany.getTotalPrice();

                }
            }else {
                sum += product.getTotalPrice();
            }
        }
        return sum;
    }

    public Double getCurrentMonthRevenueForWebsite(){

        Double sum = 0.0;
        List<OrderProduct> orderProducts = orderRepository.findOrdersCurrentMonth();
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                for (OrderCompany orderCompany : product.getOrderCompanySet() ){
                    sum += product.getTotalPrice() - orderCompany.getTotalPrice();

                }
            }else {
                sum += product.getTotalPrice();
            }
        }
        return sum;
    }

    public Double getLastMonthRevenueForWebsite(){

        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonthYearMonth = YearMonth.from(currentDate).minusMonths(1);
        int lastMonthYear = lastMonthYearMonth.getYear();
        int lastMonth = lastMonthYearMonth.getMonthValue();
        Double sum = 0.0;
        List<OrderProduct> orderProducts = orderRepository.findDeliveredOrdersLastMonth(lastMonthYear, lastMonth);
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                for (OrderCompany orderCompany : product.getOrderCompanySet() ){
                    sum += product.getTotalPrice() - orderCompany.getTotalPrice();

                }
            }else {
                sum += product.getTotalPrice();
            }
        }
        return sum;
    }

    public Integer countAllProductSoldForWebsite(){

        Integer count = 0;
        List<OrderProduct> orderProducts = orderRepository.findAllByStatus("DELIVERED");
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                    count += product.getTotalItems();


            }else {
                count += 1;
            }
        }
        return count;
    }

    public Integer countTodayProductSoldForWebsite(){

        Integer count = 0;
        List<OrderProduct> orderProducts = orderRepository.findAllByStatusAndDate();
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                count += product.getTotalItems();


            }else {
                count += 1;
            }
        }
        return count;
    }

    public Integer countCurrentMonthProductSoldForWebsite(){

        Integer count = 0;
        List<OrderProduct> orderProducts = orderRepository.findOrdersCurrentMonth();
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                count += product.getTotalItems();


            }else {
                count += 1;
            }
        }
        return count;
    }

    public Integer countLastMonthProductSoldForWebsite(){
        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonthYearMonth = YearMonth.from(currentDate).minusMonths(1);
        int lastMonthYear = lastMonthYearMonth.getYear();
        int lastMonth = lastMonthYearMonth.getMonthValue();
        Integer count = 0;
        List<OrderProduct> orderProducts = orderRepository.findDeliveredOrdersLastMonth(lastMonthYear, lastMonth);
        if (orderProducts.isEmpty()){
            throw new ApiException("No record found ");
        }
        for (OrderProduct product : orderProducts){
            if (!product.getOrderCompanySet().isEmpty()){

                count += product.getTotalItems();


            }else {
                count += 1;
            }
        }
        return count;
    }

}
