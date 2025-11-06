package com.ecomservice.service;

import com.ecomservice.entity.CartItem;
import com.ecomservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem saveCartItem(CartItem cartItem) {

        return cartItemRepository.save(cartItem);
    }
}