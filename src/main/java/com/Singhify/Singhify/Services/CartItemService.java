package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.CartItemDTO;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Exception.InsufficientStockException;
import com.Singhify.Singhify.Models.Cart;
import com.Singhify.Singhify.Models.CartItems;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Repos.CartItemsRepo;
import com.Singhify.Singhify.Repos.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    ProductServices productServices;
    @Autowired
    CartItemsRepo cartItemsRepo;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CartItemDTO cartItemDTO;


    public List<CartItems> addItemInCart(Cart cart, Long productId, int quantity) {
        List<CartItems> cartItemsList = cart.getCartItems();

        if (cartItemsList.isEmpty()) {
            cartItemsList = new ArrayList<>();
        }

        // Check if the item already exists in the cart
        CartItems cartItem = cartItemsList.stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .findFirst()
                .orElse(null);

        Product productToAdd = productServices.getProductById(productId);

        if (cartItem == null) {
            // Item not found, create a new one
            cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setProduct(productToAdd);
            cartItem.setQuantity(0);
            cartItem.setValid(true);
            cartItem.setPrice(productToAdd.getSellingPrice());
            cartItemsList.add(cartItem); // Add new item to the list
        }

        int totalQuantity = cartItem.getQuantity() + quantity;
        if (totalQuantity <= productToAdd.getQuantity()) {
            cartItem.setQuantity(totalQuantity);
            cartItem.setPrice(totalQuantity * productToAdd.getPrice());
        } else {
            throw new InsufficientStockException("Mentioned Quantity for the product not available for now.");
        }

        cartItem.setDiscount(0.0);
        cartItemsRepo.save(cartItem); // Save the updated or newly created cart item

        return cartItemsList;
    }


    public List<CartItems> deleteItemInCart(Cart cart, Long productId) {
        List<CartItems> cartItemsList = cart.getCartItems();
         if(cartItemsList.isEmpty())
         {
             throw new EntityNotFoundException("Cart Items");
         }
        CartItems cartItem = cartItemsList.stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .findFirst()
                .orElse(null);
         if(cartItem==null)
         {
             throw new EntityNotFoundException("Product in Cart","Product Id",productId);
         }
         cartItemsRepo.delete(cartItem);
         cart.setTotalPrice(cart.getTotalPrice()- cartItem.getPrice());
         cartItemsList.remove(cartItem);
         return  cartItemsList;
    }
}
