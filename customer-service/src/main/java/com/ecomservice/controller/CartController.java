package com.ecomservice.controller;

import com.ecomservice.dto.CartRequestDTO;
import com.ecomservice.entity.Cart;
import com.ecomservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/buy")
    public ResponseEntity<Cart> buyItems(@RequestBody CartRequestDTO request) {
        Cart cart = cartService.buyItems(request);
        return ResponseEntity.ok(cart);
    }
}
