package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.CartDTO;
import com.Singhify.Singhify.Data.DTO.CartItemDTO;
import com.Singhify.Singhify.Models.Cart;
import com.Singhify.Singhify.Models.CartItems;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.CartRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    UserServices userServices;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartItemService cartItemServices;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CartDTO cartDTO;
    @Autowired
    List<CartItemDTO> cartItemsDTO;

    public CartDTO addProduct(Long productId, int quantity) {

        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCart(loggedInUser);

        List<CartItems> cartItemsList=
                cartItemServices.addItemInCart(cart,productId,quantity);

        Double productPrice= (Double) cartItemsList.getLast().getPrice();
        cart.setTotalPrice(Double.valueOf(cart.getTotalPrice()+productPrice));
        Cart savedCart=cartRepo.save(cart);

        mapper.map(savedCart,cartDTO);

        List<CartItemDTO> cartItemDTOList = cartItemsList.stream()
                .map(cartItem ->customMaptoDto(cartItem))
                .toList();

        cartDTO.setCartItemDtos(cartItemDTOList);

        return cartDTO;

    }


    private CartItemDTO customMaptoDto(CartItems cartItem) {
        CartItemDTO cartItemDTO=new CartItemDTO();
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setCartId(cartItem.getCart().getCartId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setDiscount(cartItem.getDiscount());
        cartItemDTO.setValid(cartItem.getValid());
        cartItemDTO.setProduct(cartItem.getProduct());
        return cartItemDTO;
    }


    private Cart returnCart(Users user)
    {
        int userId=user.getUserId();
        Cart cart=cartRepo.findByUser_UserId(userId);
        if(cart==null)
        {
            cart= new Cart();
            cart.setUser(user);
        }
        return cart;
    }

    public CartDTO deleteProduct(Long productId) {
        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCart(loggedInUser);

        List<CartItems> cartItemsList=
                cartItemServices.deleteItemInCart(cart,productId);

        Cart savedCart=cartRepo.save(cart);

        List<CartItemDTO> cartItemDTOList = cartItemsList.stream()
                .map(cartItem ->customMaptoDto(cartItem))
                .toList();

        mapper.map(savedCart,cartDTO);

        cartDTO.setCartItemDtos(cartItemDTOList);
        return cartDTO;

    }
}
