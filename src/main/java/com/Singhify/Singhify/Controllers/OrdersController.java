package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Data.DTO.OrderItemsDTO;
import com.Singhify.Singhify.Data.DTO.OrdersDTO;
import com.Singhify.Singhify.Models.Orders;
import com.Singhify.Singhify.Services.OrdersService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;



    @PostMapping("/add/product/{productId}/quantity/{quantity}")
    public ResponseEntity<Orders> createOrderFromProduct(@PathVariable long productId,int quantity)
    {
        OrdersDTO ordersDTO=ordersService.addProductToOrder(productId,quantity);
    }





}
