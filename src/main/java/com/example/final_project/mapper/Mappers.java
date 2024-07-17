package com.example.final_project.mapper;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.*;
import com.example.final_project.dto.responsedDto.*;
import com.example.final_project.entity.*;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public UserResponseDto convertToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public User convertRegisterDTOToUser(UserRegisterRequestDto userRegisterRequestDto) {
        return modelMapper.map(userRegisterRequestDto, User.class);
    }

    public User convertResponceDTOToUser(UserResponseDto userRequestDto) {
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
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
        });
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

    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {;
        return OrderItem.builder()
                .quantity(orderItemRequestDto.getQuantity())
                .product(productRepository.findById(orderItemRequestDto.getProductId())
                        .orElseThrow(() -> new BadRequestException("Requested product was not found")))
                .build();
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
