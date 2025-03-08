package com.electronicstore.service;

import java.util.List;

import com.electronicstore.dto.CreateOrderRequest;
import com.electronicstore.dto.OrderDto;
import com.electronicstore.dto.PageableResponse;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    //order methods(logic) related to order

}
