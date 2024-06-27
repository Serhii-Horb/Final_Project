package com.example.final_project.controller;

import com.example.final_project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartService cartService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CartDto insertCart(@RequestBody CartDto cartDto) {
//        return cartService.insertCart(cartDto);
//    }
}
