package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.OrderItemsDTO;
import com.Singhify.Singhify.Data.DTO.OrdersDTO;
import com.Singhify.Singhify.Models.OrderItems;
import com.Singhify.Singhify.Models.Orders;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {


    @Autowired
    OrderItemsService orderItemsService;
    @Autowired
    UserServices userServices;

    @Autowired
    OrderRepo orderRepo;

    public OrdersDTO addProductsToOrderFromCart(int cartId) {
        Users loggedInUser= userServices.getLoggedInUserId();

        List<OrderItems> orderItemsList =orderItemsService.addProductsFromCart(cartId);

        double totalItemsAmount=orderItemsList.stream().mapToDouble(orderItems->orderItems.getItem_amount()).sum();
        double totalTaxAmount=orderItemsList.stream().mapToDouble(orderItems->orderItems.getTax_amount()).sum();

        Orders order=createNewOrder(loggedInUser,orderItemsList,totalItemsAmount,totalTaxAmount,0);

        Orders savedOrder=orderRepo.save(order);
        orderItemsList.stream().forEach(orderItems -> orderItems.setOrder(savedOrder));

        return customMaptoDto(savedOrder);

    }
    private Orders createNewOrder(Users loggedInUser,List<OrderItems> orderItemsList, double totalItemsAmount, double totalTaxAmount, double discountAmt) {
        Orders order=new Orders();
        order.setUsers(loggedInUser);
        order.setOrder_date(LocalDateTime.now());
        order.setCreated_on(LocalDateTime.now());
        order.setAmount(totalItemsAmount);
        order.setTax_amount(totalTaxAmount);
        order.setTotal_amount(totalItemsAmount+totalTaxAmount);
        order.setDiscountAmt(0);
        order.setFinal_amount(order.getTotal_amount()- order.getDiscountAmt());
        order.setOrderItems(orderItemsList);
        return order;
    }
    private OrdersDTO customMaptoDto(Orders order) {
        OrdersDTO ordersDTO=new OrdersDTO();
        ordersDTO.setOrder_id(order.getOrder_id());
        ordersDTO.setAmount(ordersDTO.getAmount());
        ordersDTO.setTax_amount(ordersDTO.getTax_amount());
        ordersDTO.setTotal_amount(order.getTotal_amount());
        ordersDTO.setDiscountAmt(order.getDiscountAmt());
        ordersDTO.setFinal_amount(order.getFinal_amount());
        ordersDTO.setOrder_date(order.getOrder_date());
        ordersDTO.setCreated_on(order.getCreated_on());
        ordersDTO.setUpdated_on(order.getUpdated_on());
        ordersDTO.setPaymentId(order.getPayment().getPayment_id());
        ordersDTO.setUserId(order.getUsers().getUserId());
        List<OrderItemsDTO> orderItemsDTOList=
                order.getOrderItems().stream().map(orderItems -> customMaptoDto(orderItems)).toList();
        ordersDTO.setOrderItemsDTOS(orderItemsDTOList);
        return  ordersDTO;
    }

    private OrderItemsDTO customMaptoDto(OrderItems orderItems) {
        OrderItemsDTO orderItemsDTO=new OrderItemsDTO();
        orderItemsDTO.setId(orderItems.getId());
        orderItemsDTO.setItem_amount(orderItems.getItem_amount());
        orderItemsDTO.setQuantity(orderItems.getQuantity());
        orderItemsDTO.setTax_amount(orderItems.getTax_amount());
        orderItemsDTO.setProductName(orderItems.getProduct().getProductName());
        orderItemsDTO.setProductId(orderItems.getProduct().getId());
        orderItems.setTotal_amount(orderItems.getTotal_amount());
        return  orderItemsDTO;
    }

    public OrdersDTO addProductToOrder(long productId, int quantity) {
        Users loggedInUser= userServices.getLoggedInUserId();
        OrderItems orderItems=orderItemsService.addProducts(productId,quantity);

        List<OrderItems> orderItemsList=new ArrayList<>();
        orderItemsList.add(orderItems);

        double totalItemsAmount=orderItems.getItem_amount();
        double totalTaxAmount=orderItems.getTax_amount();

        Orders order=createNewOrder(loggedInUser,orderItemsList,totalItemsAmount,totalTaxAmount,0);

        Orders savedOrder=orderRepo.save(order);
        orderItems.setOrder(savedOrder);

        return customMaptoDto(savedOrder);
    }




}
