package com.ecomservice.service;

import com.ecomservice.dto.CartRequestDTO;
import com.ecomservice.entity.Cart;

import java.util.List;

public interface CartService {

    Cart buyItems(CartRequestDTO request);
}