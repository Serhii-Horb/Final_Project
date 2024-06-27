package com.example.final_project.controller;

import com.example.final_project.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cartitems")
public class CartItemController {
//    private final CartItemService cartItemService;
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<CartItemDto> getCartItem() {
//        return cartItemService.getCartItem();
//    }
//
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CartItemDto getCartItemById(@PathVariable Long id) {
//        return cartItemService.getCartItemById(id);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CartItemDto insertCartItem(@RequestBody CartItemDto cartItemDto) {
//        return cartItemService.insertCartItem(cartItemDto);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CartItemDto updateCartItem(@RequestBody CartItemDto cartItemDto) {
//        return cartItemService.updateCartItem(cartItemDto);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCartItemById(@PathVariable Long id) {
//        cartItemService.deleteCartItemById(id);
//    }
}
