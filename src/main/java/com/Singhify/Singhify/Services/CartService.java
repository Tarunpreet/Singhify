package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.APIResponses.PaginatedAPIResponse;
import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.CartDTO;
import com.Singhify.Singhify.Data.DTO.CartItemDTO;
import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Models.Cart;
import com.Singhify.Singhify.Models.CartItems;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.CartRepo;
import com.Singhify.Singhify.Utilities.MappingData;
import com.Singhify.Singhify.Utilities.PaginationValid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @Autowired
    private MappingData<CartItems, CartItemDTO> mappingData;


    public CartDTO addProduct(Long productId, int quantity) {

        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCartOrCreateNew(loggedInUser,true);

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







    public CartDTO deleteProduct(Long productId) {
        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCartOrCreateNew(loggedInUser,false);

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
    public CartDTO reduceQuantity(Long productId, int quantity) {
        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCartOrCreateNew(loggedInUser,false);

        List<CartItems> cartItemsList=
                cartItemServices.reduceQuantity(cart,productId,quantity);

        List<CartItemDTO> cartItemDTOList = cartItemsList.stream()
                .map(cartItem ->customMaptoDto(cartItem))
                .toList();
        Cart savedCart=cartRepo.save(cart);

        mapper.map(savedCart,cartDTO);

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
    private Cart returnCartOrCreateNew(Users user,Boolean createNew)
    {
        int userId=user.getUserId();
        Cart cart=cartRepo.findByUser_UserId(userId);
        if(cart==null)
        {
            if(!createNew)
            {
                throw new EntityNotFoundException("Cart");
            }
            cart= new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
        return cart;
    }

    public CartDTO addQuantity(Long productId, int quantity) {
        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCartOrCreateNew(loggedInUser,true);

        List<CartItems> cartItemsList=
                cartItemServices.addQuantityInCart(cart,productId,quantity);
        List<CartItemDTO> cartItemDTOList = cartItemsList.stream()
                .map(cartItem ->customMaptoDto(cartItem))
                .toList();
        Cart savedCart=cartRepo.save(cart);

        mapper.map(savedCart,cartDTO);

        cartDTO.setCartItemDtos(cartItemDTOList);
        return cartDTO;

    }

    public CartDTO showCart(int pageNumber, int pageSize) {
        PaginationValid.checkParameters(pageSize,pageNumber);
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize);
        Users loggedInUser= userServices.getLoggedInUserId();
        Cart cart=returnCartOrCreateNew(loggedInUser,false);

        Page<CartItems> cartItemsPage = cartItemServices.getCartItems(cart, pageDetails);

        List<CartItemDTO> cartItemDTOList = cartItemsPage.getContent().stream()
                .map(cartItem ->customMaptoDto(cartItem))
                .toList();

        PaginatedAPIResponse<CartItemDTO> paginatedResponse = new PaginatedAPIResponse<>();
        paginatedResponse.setContent(cartItemDTOList);

        // Add metadata using the utility method
        mappingData.mappingPageMetaData(cartItemsPage, paginatedResponse);
        CartDTO cartDTO = mapper.map(cart, CartDTO.class);
        cartDTO.setCartItemDtos(paginatedResponse);

        return cartDTO;
    }
}
