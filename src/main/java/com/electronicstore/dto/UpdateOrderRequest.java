package com.electronicstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {

    private String orderStatus;
    private String paymentStatus;

}