package com.Singhify.Singhify.Controllers;


import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.CartDTO;
import com.Singhify.Singhify.Data.DTO.CategoryDTO;
import com.Singhify.Singhify.Services.CartItemService;
import com.Singhify.Singhify.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;


    @GetMapping("/show")
    public ResponseEntity<CartDTO> showCart(@RequestParam(name = "pageNumber",required = false,
                                             defaultValue = AppConstants.pageNumber) int pageNumber,
                                            @RequestParam (name = "pageSize",required = false,
                                             defaultValue = AppConstants.pageSize) int pageSize)
    {
        CartDTO cartDTO=cartService.showCart(pageNumber,pageSize);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

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
    @PostMapping("/{productId}/reduceQuantity/{quantity}")
    public ResponseEntity<CartDTO> reduceQuantity(@PathVariable Long productId,@PathVariable int quantity)
    {
        CartDTO cartDTO=cartService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
    @PostMapping("/{productId}/addQuantity/{quantity}")
    public ResponseEntity<CartDTO> addQuantity(@PathVariable Long productId,@PathVariable int quantity)
    {
        CartDTO cartDTO=cartService.addQuantity(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }




}
