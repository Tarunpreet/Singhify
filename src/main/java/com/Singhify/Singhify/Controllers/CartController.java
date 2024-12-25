package com.Singhify.Singhify.Controllers;


import com.Singhify.Singhify.Data.DTO.CartDTO;
import com.Singhify.Singhify.Data.DTO.CategoryDTO;
import com.Singhify.Singhify.Services.CartItemService;
import com.Singhify.Singhify.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @PostMapping("/addProduct/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductInCart(@PathVariable Long productId,
                                                    @PathVariable int quantity)
    {
        CartDTO cartDTO=cartService.addProduct(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
    @PostMapping("/deleteProduct/{productId}")
    public ResponseEntity<CartDTO> deleteProductInCart(@PathVariable Long productId)
    {
        CartDTO cartDTO=cartService.deleteProduct(productId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


}
