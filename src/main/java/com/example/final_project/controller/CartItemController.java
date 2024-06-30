package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.CartItemRequestDto;
import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.CartItemResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.service.CartItemService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDto addProductInCart(@RequestBody CartItemRequestDto cartItemRequestDto, @PathVariable @Valid @Min(1) Long id) {
       return cartItemService.addOrUpdateProductInCart(cartItemRequestDto, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductInCart(@RequestBody Long product, @PathVariable @Valid @Min(1) Long id) {
        cartItemService.deleteProductInCartByUserIdAndProductId(product, id);
    }

}