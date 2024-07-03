package com.example.final_project.mapper;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.*;
import com.example.final_project.dto.responsedDto.*;
import com.example.final_project.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;


    public UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto usersResponseDto = modelMapper.map(user, UserResponseDto.class);
        usersResponseDto.setPassword("******");
        return usersResponseDto;
    }

    public User convertToUser(UserRegisterRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    public Cart convertToCart(CartRequestDto cartRequestDto) {
        return modelMapper.map(cartRequestDto, Cart.class);
    }

    public CartResponseDto convertToCartResponseDto(Cart cart) {
        return modelMapper.map(cart, CartResponseDto.class);
    }

    public FavoriteResponseDto convertToFavoriteResponseDto(Favorite favorites) {
        return modelMapper.map(favorites, FavoriteResponseDto.class);
    }

    public Favorite convertToFavorite(FavoriteResponseDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorite.class);
    }

    public Order convertToOrder(OrderRequestDto ordersRequestDto) {
        List<OrderItem> orderItems = MapperUtil.convertList(ordersRequestDto.getOrderItemsList(), this::convertToOrderItem);
        Order order = modelMapper.map(ordersRequestDto, Order.class);
        order.setItems(orderItems);
        return order;
    }

    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemResponseDto.class);
    }

    public CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemResponseDto.class);
    }

    public CartItem convertToCartItem(CartItemRequestDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }

    public OrderResponseDto convertToOrderResponseDto(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {
        return modelMapper.map(orderItemRequestDto, OrderItem.class);
    }

    public Product convertToProduct(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto, Product.class);
    }

    public ProductResponseDto convertToProductResponseDto(Product product) {
        modelMapper.typeMap(Product.class, ProductResponseDto.class);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public CategoryResponseDto convertToCategoryResponseDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public Category convertToCategory(CategoryRequestDto categoryRequestDto) {
        return modelMapper.map(categoryRequestDto, Category.class);
    }
}
