package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Exception.InsufficientStockException;
import com.Singhify.Singhify.Models.Cart;
import com.Singhify.Singhify.Models.CartItems;
import com.Singhify.Singhify.Models.OrderItems;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Repos.CartRepo;
import com.Singhify.Singhify.Repos.OrderItemsRepo;
import com.Singhify.Singhify.Repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemsService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrderItemsRepo orderItemsRepo;

    public List<OrderItems> addProductsFromCart(int cartId) {
        Cart cart=cartRepo.findById(cartId).
                orElseThrow(()->new EntityNotFoundException("Cart","Id",cartId));
        List<CartItems> cartItemsList =cart.getCartItems();

        List<OrderItems> orderItemsList= cartItemsList.stream().filter(CartItems::getValid).map(cartItems -> mapCartItemToOrderItem(cartItems)).toList();

        return orderItemsList;

    }

    private OrderItems mapCartItemToOrderItem(CartItems cartItems) {
        OrderItems orderItems=new OrderItems();
        orderItems.setProduct(cartItems.getProduct());
        orderItems.setItem_amount(cartItems.getProduct().getSellingPrice());
        orderItems.setTax_amount(0.0);
        orderItems.setTotal_amount(orderItems.getItem_amount()+orderItems.getTax_amount());
        orderItems.setQuantity(cartItems.getQuantity());
        OrderItems savedOrderItem=orderItemsRepo.save(orderItems);
        return orderItems;
    }

    public OrderItems addProducts(long productId, int quantity) {
        Product product=productRepo.findById(productId).
                orElseThrow(()->new EntityNotFoundException("Product","Id",productId));
        if(product.getQuantity()<quantity)
        {
            throw new InsufficientStockException("The quantity mentioned for product is not available right now.");
        }
        OrderItems orderItems=new OrderItems();
        orderItems.setProduct(product);
        orderItems.setItem_amount(product.getSellingPrice());
        orderItems.setTax_amount(0.0);
        orderItems.setTotal_amount(orderItems.getItem_amount()+orderItems.getTax_amount());
        orderItems.setQuantity(product.getQuantity());
        OrderItems savedOrderItem=orderItemsRepo.save(orderItems);
        return orderItems;


    }
}
