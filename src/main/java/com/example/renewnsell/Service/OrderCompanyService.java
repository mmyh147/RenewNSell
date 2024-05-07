package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.OrderCompany;
import com.example.renewnsell.Model.Product;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.OrderCompanyRepository;
import com.example.renewnsell.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderCompanyService {

    private final OrderCompanyRepository orderCompanyRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    public List<OrderCompany> getAll() {
        if (orderCompanyRepository.findAll().isEmpty())
            throw new ApiException("order companies is empty");
        return orderCompanyRepository.findAll();
    }

    public void add(Integer companyId, OrderCompany orderCompany) {
        Company company = companyRepository.findCompanyById(companyId);
        orderCompany.setCompany(company);
        orderCompanyRepository.save(orderCompany);
    }

    public void update(Integer companyId, Integer orderId, OrderCompany orderCompany) {
        Company company = companyRepository.findCompanyById(companyId);
        OrderCompany orderCompany1 = orderCompanyRepository.findOrderCompanyById(orderId);
        if (orderCompany1 == null)
            throw new ApiException("order company not found");
        if (orderCompany1.getCompany().getId() != companyId)
            throw new ApiException("this order not unauthorized for you");
        orderCompany1.setProducts(orderCompany.getProducts());
        orderCompany1.setStatus(orderCompany.getStatus());
        orderCompany1.setTotalPrice(orderCompany.getTotalPrice());
        orderCompany1.setDate(orderCompany.getDate());
        orderCompanyRepository.save(orderCompany1);
    }


    public void delete(Integer companyId, Integer orderId) {
        Company company = companyRepository.findCompanyById(companyId);
        OrderCompany orderCompany1 = orderCompanyRepository.findOrderCompanyById(orderId);
        if (orderCompany1 == null)
            throw new ApiException("order company not found");
        if (orderCompany1.getCompany().getId() != companyId)
            throw new ApiException("this order not unauthorized for you");
        orderCompanyRepository.delete(orderCompany1);
    }

    //==========================================CRUD DONE BY GHALIAH ======

    //===================================================
    public void changeStatus(Integer companyId, Integer orderId) {
        OrderCompany order = orderCompanyRepository.findOrderCompanyById(orderId);
        if (order == null) {
            throw new ApiException("Order Not Found ");
        }
        if (order.getCompany().getId() != companyId)
            throw new ApiException("this order not unauthorized for you");

        if (order.getStatus().equalsIgnoreCase("CANCELED"))
            throw new ApiException("you can't change canceled order");
        if (order.getStatus().equalsIgnoreCase("DELIVERED"))
            throw new ApiException("order already is delivered ");
        switch (order.getStatus()) {
            case "PENDING":
                order.setStatus("ORDER_CONFIRMED");
                orderCompanyRepository.save(order);
                break;
            case "ORDER_CONFIRMED":
                order.setStatus("PREPARING");
                orderCompanyRepository.save(order);
                break;
            case "PREPARING":
                order.setStatus("SHIPPED");
                orderCompanyRepository.save(order);
                break;
            case "SHIPPED":
                order.setStatus("OUT_FOR_DELIVERY");
                orderCompanyRepository.save(order);
                break;
            case "OUT_FOR_DELIVERY":
                order.setStatus("DELIVERED");
                orderCompanyRepository.save(order);
                break;

        }
    }


    public Set<OrderCompany> findAllByCompanyId(Integer companyId) {
        Company company = companyRepository.findCompanyById(companyId);
        if (company.getOrders().isEmpty())
            throw new ApiException("no product bought yet so list is empty");
        return company.getOrders();
    }


    //=======================================MOHAMMED==================


    //================================= [getOrderProductByPercentOfDefective  ] METHOD DONE BY GHALIAH  ==============================

    //getOrderProductByPercentOfDefective
    public List<OrderCompany> getOrderProductByPercentOfDefective(Integer companyId, Double precent) {
        List<Product> products = productRepository.findProductsByCompanyIdAndPercentOfDefective(companyId, precent);
        if (products.isEmpty())
            throw new ApiException("no product with PercentOfDefective: " + precent);
        List<OrderCompany> orderCompanies = new ArrayList<>();
        for (Product product : products) {
            orderCompanies.addAll(product.getOrderCompany());
        }
        if (orderCompanies.isEmpty())
            throw new ApiException("Order Company for products with PercentOfDefective: " + precent);

        return orderCompanies;

    }


    public Double getTotalProfitForOneProduct(Integer companyId, Integer productId) {
        Product product = productRepository.findProductById(productId);
        Double totalProfitForOneOrdersProduct = 0.0;
        if (check(companyId, productId)) {
            if (product.getOrderProduct().isEmpty())
                throw new ApiException("No Order Company for this product: " + productId);
           // for (OrderCompany orderCompany : product.getOrderCompany())
                totalProfitForOneOrdersProduct +=
                        getTotalProfitForOneProductWithFixPrice(companyId, productId) + getTotalProfitForOneProductWithOutFixPrice(companyId, productId);

        }
        return totalProfitForOneOrdersProduct;
    }

    public Double getTotalProfitForOneProductWithFixPrice(Integer companyId, Integer productId) {
        Product product = productRepository.findProductById(productId);
        Double totalProfitForOneOrdersProduct = 0.0;
        if (check(companyId, productId)) {
            if (product.getOrderProduct().isEmpty())
                throw new ApiException("No Order Company for this product: " + productId);
            for (OrderCompany orderCompany : product.getOrderCompany()) {
                if (orderCompany.getBuyWithFix()) {
                    totalProfitForOneOrdersProduct += orderCompany.getTotalPrice() + product.getFixPrice();
                }
            }
        }
        return totalProfitForOneOrdersProduct;
    }

    public boolean check(Integer companyId, Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        if (product.getCompany().getId() != companyId)
            throw new ApiException("this product unauthorized for you");
        return true;
    }

    public Double getTotalProfitForOneProductWithOutFixPrice(Integer companyId, Integer productId) {
        Product product = productRepository.findProductById(productId);
        Double totalProfitForOneOrdersProduct = 0.0;
        if (check(companyId, productId)) {
            if (product.getOrderProduct().isEmpty())
                throw new ApiException("No Order Company for this product: " + productId);
            for (OrderCompany orderCompany : product.getOrderCompany()) {
                if (!orderCompany.getBuyWithFix()) {
                    totalProfitForOneOrdersProduct += orderCompany.getTotalPrice();
                }
            }
        }
        return totalProfitForOneOrdersProduct;
    }
    //=======================================================

    public Integer getTotalNumberOrdersForOneProduct(Integer companyId, Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (check(companyId, productId)) {
            if (product.getOrderProduct().isEmpty())
                throw new ApiException("No Order Company for this product: " + productId);
            return product.getOrderProduct().size();

        }
        return product.getOrderProduct().size();

    }

    //================================= [getTotalNumberOfOrdersCompany  ] METHOD DONE BY HAYA  ==============================
    public Integer getTotalNumberOfOrdersCompany(Integer companyId) {
        Company company = companyRepository.findCompanyById(companyId);
        if (company.getOrders().isEmpty())
            throw new ApiException("no product bought yet so list is empty");
        return company.getOrders().size();
    }

    public double getAverageProfitByCompanyName(Integer companyId) {
            Company company = companyRepository.findCompanyById(companyId);
            if (company.getOrders().isEmpty())
                throw new ApiException("no product bought yet so list is empty");

            double total = 0.0;
            Set<OrderCompany> orders = company.getOrders();
            if (orders.isEmpty())
                throw new ApiException("there are no orders for the company");

            Set<OrderCompany> list = company.getOrders();
            for (OrderCompany orderCompany : list) {
                if(orderCompany.getStatus().equalsIgnoreCase("DELIVERED"))
                    total += orderCompany.getTotalPrice();

            }

            return total / company.getOrders().size();
        }

    public Double getTotalProfitForCompany(Integer companyId){
//        Company company = companyRepository.findCompanyById(companyId);
//        Double total=0.0;
//        if (company.getOrders().isEmpty())
//            throw new ApiException("no product bought yet so list is empty");
//        for (OrderCompany orderCompany:company.getOrders()){
//            total+=orderCompany.getTotalPrice();
//        }
//        return total;
        Double total = 0.0;
        total = orderCompanyRepository.findTotalProfitForCompany(companyId);
        if (total == null) {
            throw new ApiException("No profit recorded for the company.");
        }
        return total;
    }


    public Double getTodayProfitForCompany(Integer companyId) {
        LocalDate today = LocalDate.now();
        Double total = orderCompanyRepository.findTotalProfitForCompanyToday(companyId, today);
        if (total == null) {
            throw new ApiException("No profit recorded for today.");
        }
        return total;
    }

    public Double getCurrentMonthProfitForCompany(Integer companyId) {
        LocalDate currentDate = LocalDate.now();
        Double total = orderCompanyRepository.findTotalProfitForCompanyCurrentMonth(companyId, currentDate);
        if (total == null) {
            throw new ApiException("No profit recorded for the current month.");
        }
        return total;
    }

    public Double getLastMonthProfitForCompany(Integer companyId) {
        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonthYearMonth = YearMonth.from(currentDate).minusMonths(1);
        int lastMonthYear = lastMonthYearMonth.getYear();
        int lastMonth = lastMonthYearMonth.getMonthValue();
        Double total = orderCompanyRepository.findTotalProfitForCompanyLastMonth(companyId, lastMonthYear, lastMonth);
        if (total == null) {
            throw new ApiException("No profit recorded for the last month.");
        }
        return total;
    }




    public Integer getTotalProductsSoldForCompany(Integer companyId) {
        return orderCompanyRepository.countAllProductsSoldForCompany(companyId);
    }

    public Integer countProductsSoldTodayForCompany(Integer companyId) {
        return orderCompanyRepository.countProductsSoldTodayForCompany(companyId);
    }

    public Integer countProductsSoldCurrentMonthForCompany(Integer companyId) {
        return orderCompanyRepository.countProductsSoldCurrentMonthForCompany(companyId);
    }

    public Integer countProductsSoldLastMonthForCompany(Integer companyId) {
        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonthYearMonth = YearMonth.from(currentDate).minusMonths(1);
        int lastMonthYear = lastMonthYearMonth.getYear();
        int lastMonth = lastMonthYearMonth.getMonthValue();
        Integer result = orderCompanyRepository.countProductsSoldForCompanyLastMonth(companyId,lastMonthYear, lastMonth);
        if (result == null){
            throw new ApiException("No record found for last month");
        }
        return result;

    }




}
