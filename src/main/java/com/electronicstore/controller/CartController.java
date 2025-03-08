package com.electronicstore.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.electronicstore.config.AppConstants;
import com.electronicstore.dto.AddItemToCartRequest;
import com.electronicstore.dto.ApiResponseMessage;
import com.electronicstore.dto.CartDto;
import com.electronicstore.service.CartService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/carts")
@Api(value = "CartController",description = "API For Cart!!!")
public class CartController {

    @Autowired
    private CartService cartService;

    //add items to cart
    
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    @PostMapping("/{userId}")
   

    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    @DeleteMapping("/{userId}/items/{itemId}")
   

    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Item is removed !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //clear cart
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    @DeleteMapping("/{userId}")
    

    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Now cart is blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //add items to cart
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    @GetMapping("/{userId}")
    

    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}



