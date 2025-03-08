package com.electronicstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronicstore.config.AppConstants;
import com.electronicstore.dto.ApiResponseMessage;
import com.electronicstore.dto.CreateOrderRequest;
import com.electronicstore.dto.OrderDto;
import com.electronicstore.dto.PageableResponse;
import com.electronicstore.service.OrderService;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
@Api(value = "OrderController",description = "API For Making Orders!!!")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")

    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("order is removed !!")
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")

    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")

    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}

