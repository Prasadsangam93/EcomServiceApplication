package com.ecomservice.service;

import com.ecomservice.dto.CartRequestDTO;
import com.ecomservice.dto.ItemRequestDTO;
import com.ecomservice.entity.Cart;
import com.ecomservice.entity.CartItem;
import com.ecomservice.repository.CartItemRepository;
import com.ecomservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public Cart buyItems(CartRequestDTO request) {
        List<CartItem> purchasedItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (ItemRequestDTO itemReq : request.getItems()) {
            CartItem existingItem = cartItemRepository.findById(itemReq.getCartItemId())
                    .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + itemReq.getCartItemId()));

            if (existingItem.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for: " + existingItem.getProductName());
            }

            // reduce stock
            existingItem.setQuantity(existingItem.getQuantity() - itemReq.getQuantity());
            cartItemRepository.save(existingItem);

            // copy purchased item
            CartItem purchasedCopy = new CartItem();
            purchasedCopy.setProductName(existingItem.getProductName());
            purchasedCopy.setPrice(existingItem.getPrice());
            purchasedCopy.setQuantity(itemReq.getQuantity());

            // save purchased copy first (fix)
            CartItem savedItem = cartItemRepository.save(purchasedCopy);

            purchasedItems.add(savedItem);
            totalAmount += existingItem.getPrice() * itemReq.getQuantity();
        }

        Cart cart = new Cart();
        cart.setTotalAmount(totalAmount);
        cart.setItems(purchasedItems);

        return cartRepository.save(cart);
    }
}
