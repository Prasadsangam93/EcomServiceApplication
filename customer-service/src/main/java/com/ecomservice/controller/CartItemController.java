package com.ecomservice.controller;

import com.ecomservice.entity.CartItem;
import com.ecomservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartitems")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/save")
    public ResponseEntity<CartItem> saveCartItem(@RequestBody CartItem cartItem) {
        CartItem saved = cartItemService.saveCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
