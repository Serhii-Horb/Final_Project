package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.CartItemRequestDto;
import com.example.final_project.dto.responsedDto.CartItemResponseDto;
import com.example.final_project.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
@Tag(name = "Cart controller.", description = "All manipulations with cart data are carried out here.")
public class CartItemController {
    private final CartItemService cartItemService;

    /**
     * Endpoint to add a product to the user's cart.
     *
     * @param cartItemRequestDto the request body containing product and quantity details.
     * @param id                 the ID of the user whose cart is being updated.
     * @return the response containing details of the added or updated cart item.
     */
    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Add product in Cart.",
            description = "Allows the user to add the desired product to their cart."
    )
    public CartItemResponseDto addProductInCart(@RequestBody CartItemRequestDto cartItemRequestDto,
                                                @PathVariable @Valid @Min(1) Long id) {
        return cartItemService.addOrUpdateProductInCart(cartItemRequestDto, id);
    }

    /**
     * Endpoint to remove a product from the user's cart.
     *
     * @param product the ID of the product to be removed.
     * @param id      the ID of the user whose cart is being updated.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete product in Cart.",
            description = "Allows the user to remove the desired item from the cart."
    )
    public void deleteProductInCart(@RequestBody Long product,
                                    @PathVariable @Valid @Min(1) Long id) {
        cartItemService.deleteProductInCartByUserIdAndProductId(product, id);
    }
}