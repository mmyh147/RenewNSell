package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.OrderCompany;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.OrderCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderCompanyService {

    private final OrderCompanyRepository orderCompanyRepository;
    private final CompanyRepository companyRepository;

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
    //===================================================
    public void changeStatus(Integer companyId,Integer orderId) {
        OrderCompany order = orderCompanyRepository.findOrderCompanyById(orderId);
        if (order == null) {
            throw new ApiException("Order Not Found ");
        }
        if (order.getCompany().getId()!=companyId)
            throw new ApiException("this order not unauthorized for you");
        //"PREPARING|SHIPPED|DELIVERED|ORDER_CONFIRMED|OUT_OF_DELIVERY"
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
                order.setStatus("OUT_OF_DELIVERY");
                orderCompanyRepository.save(order);
                break;
            case "OUT_OF_DELIVERY":
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


    //=======================================


    public Double getTodayProfitForCompany(Integer companyId){
        Company company = companyRepository.findCompanyById(companyId);
        Double total=0.0;
        if (company.getOrders().isEmpty())
            throw new ApiException("no product bought yet so list is empty");
        for (OrderCompany orderCompany:company.getOrders()){
            total+=orderCompany.getTotalPrice();
        }
        return total;
    }



}
