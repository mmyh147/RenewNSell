package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.DTO.DTOResponseFixProduct;
import com.example.renewnsell.Model.FixProduct;
import com.example.renewnsell.Model.OrderProduct;
import com.example.renewnsell.Model.ResponseFixProduct;
import com.example.renewnsell.Repository.FixProductRepository;
import com.example.renewnsell.Repository.OrderRepository;
import com.example.renewnsell.Repository.ResponseFixProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service@RequiredArgsConstructor
public class ResponseFixProductService {

    private final ResponseFixProductRepository responseFixProductRepository;
private final FixProductRepository fixProductRepository;
private final OrderRepository orderRepository;

    public List<ResponseFixProduct> getAll() {
        if (responseFixProductRepository.findAll().isEmpty())
            throw new ApiException("responseFixProduct empty list");
        return responseFixProductRepository.findAll();

    }

    public void add(Integer fixProductId,DTOResponseFixProduct responseFixProduct) {
        ResponseFixProduct responseFixProduct1=new ResponseFixProduct();
        responseFixProduct1.setPrice(responseFixProduct.getPrice());
        responseFixProduct1.setDescription(responseFixProduct.getDescription());
        OrderProduct fixProduct=orderRepository.findOrderProductById(fixProductId);
        if (fixProduct.getFixProduct()==null)throw new ApiException("not found fixProduct");
        FixProduct fixProduct1=fixProduct.getFixProduct();
        fixProduct1.setResponseFixProduct(responseFixProduct1);
        responseFixProduct1.setFixProduct(fixProduct1);
        responseFixProductRepository.save(responseFixProduct1);
        fixProductRepository.save(fixProduct1);
    }

    public void update(Integer id, DTOResponseFixProduct responseFixProduct) {
        ResponseFixProduct responseFixProduct1 = responseFixProductRepository.findResponseFixProductById(id);
        if (responseFixProduct == null) throw new ApiException("Not found");
        responseFixProduct1.setDescription(responseFixProduct.getDescription());
        responseFixProduct1.setPrice(responseFixProduct.getPrice());
        responseFixProductRepository.save(responseFixProduct1);
    }

public void delete(Integer id){
    ResponseFixProduct responseFixProduct = responseFixProductRepository.findResponseFixProductById(id);
    if (responseFixProduct == null) throw new ApiException("Not found");
responseFixProductRepository.delete(responseFixProduct);
}
//================================= ALL CRUD DONE BY GHALIAH  ==============================

    //=================================RESPONSE REQUEST FIX PRODUCT FROM EMPLOYEE DONE BY GHALIAH  ==============================
public void response(Integer fixProductId,DTOResponseFixProduct responseFixProductPostMan) {
    OrderProduct orderProduct = orderRepository.findOrderProductById(fixProductId);
    if (orderProduct.getFixProduct()==null) {
        throw new ApiException("FixProduct not found");
    }
    ResponseFixProduct responseFixProduct=orderProduct.getFixProduct().getResponseFixProduct();
    FixProduct fixProduct=orderProduct.getFixProduct();
    responseFixProduct.setDescription(responseFixProductPostMan.getDescription());
    responseFixProduct.setPrice(responseFixProductPostMan.getPrice());
    orderProduct.setTotalItems(1);
    orderProduct.setTotalPrice(responseFixProductPostMan.getPrice());
    orderProduct.setFixProduct(fixProduct);
    responseFixProduct.setFixProduct(fixProduct);
    orderRepository.save(orderProduct);
    fixProductRepository.save(fixProduct);
    responseFixProductRepository.save(responseFixProduct);

}

}

