package com.ecommerce.projectapp.response;

import com.ecommerce.projectapp.dto.OrderHistory;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {

    private String functionName;

    private Cart userCart;

    private OrderHistory orderHistory;

    private Product product;

}