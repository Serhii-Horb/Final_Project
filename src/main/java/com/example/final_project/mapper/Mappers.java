package com.example.final_project.mapper;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.*;
import com.example.final_project.dto.responsedDto.*;
import com.example.final_project.entity.*;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    /**
     * Converts a {@link User} entity to a {@link UserResponseDto}.
     *
     * @param user The User entity to be converted.
     * @return The corresponding UserResponseDto.
     */
    public UserResponseDto convertToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    /**
     * Converts a {@link UserRegisterRequestDto} to a {@link User} entity.
     *
     * @param userRegisterRequestDto The DTO containing user registration details.
     * @return The corresponding User entity.
     */
    public User convertRegisterDTOToUser(UserRegisterRequestDto userRegisterRequestDto) {
        return modelMapper.map(userRegisterRequestDto, User.class);
    }

    /**
     * Converts a {@link UserResponseDto} to a {@link User} entity.
     *
     * @param userRequestDto The DTO containing user response details.
     * @return The corresponding User entity.
     */
    public User convertResponceDTOToUser(UserResponseDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    /**
     * Converts a {@link CartRequestDto} to a {@link Cart} entity.
     *
     * @param cartRequestDto The DTO containing cart request details.
     * @return The corresponding Cart entity.
     */
    public Cart convertToCart(CartRequestDto cartRequestDto) {
        return modelMapper.map(cartRequestDto, Cart.class);
    }

    /**
     * Converts a {@link Cart} entity to a {@link CartResponseDto}.
     *
     * @param cart The Cart entity to be converted.
     * @return The corresponding CartResponseDto.
     */
    public CartResponseDto convertToCartResponseDto(Cart cart) {
        return modelMapper.map(cart, CartResponseDto.class);
    }

    /**
     * Converts a {@link Favorite} entity to a {@link FavoriteResponseDto}.
     *
     * @param favorites The Favorite entity to be converted.
     * @return The corresponding FavoriteResponseDto.
     */
    public FavoriteResponseDto convertToFavoriteResponseDto(Favorite favorites) {
        return modelMapper.map(favorites, FavoriteResponseDto.class);
    }

    /**
     * Converts a {@link FavoriteResponseDto} to a {@link Favorite} entity.
     *
     * @param favoritesDto The DTO containing favorite response details.
     * @return The corresponding Favorite entity.
     */
    public Favorite convertToFavorite(FavoriteResponseDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorite.class);
    }

    /**
     * Converts an {@link OrderRequestDto} to an {@link Order} entity, including mapping of nested {@link OrderItem} entities.
     * Sets the order items for the order and assigns the order reference to each order item.
     *
     * @param ordersRequestDto The DTO containing order request details.
     * @return The corresponding Order entity with populated order items.
     */
    public Order convertToOrder(OrderRequestDto ordersRequestDto) {
        List<OrderItem> orderItems = MapperUtil.convertList(ordersRequestDto.getOrderItemsList(), this::convertToOrderItem);
        Order order = modelMapper.map(ordersRequestDto, Order.class);
        order.setItems(orderItems);
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
        });
        return order;
    }

    /**
     * Converts an {@link OrderItem} entity to an {@link OrderItemResponseDto}.
     *
     * @param orderItem The OrderItem entity to be converted.
     * @return The corresponding OrderItemResponseDto.
     */
    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemResponseDto.class);
    }

    /**
     * Converts a {@link CartItem} entity to a {@link CartItemResponseDto}.
     *
     * @param cartItem The CartItem entity to be converted.
     * @return The corresponding CartItemResponseDto.
     */
    public CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemResponseDto.class);
    }

    /**
     * Converts a {@link CartItemRequestDto} to a {@link CartItem} entity.
     *
     * @param cartItemsDto The DTO containing cart item request details.
     * @return The corresponding CartItem entity.
     */
    public CartItem convertToCartItem(CartItemRequestDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }

    /**
     * Converts an {@link Order} entity to an {@link OrderResponseDto}.
     *
     * @param order The Order entity to be converted.
     * @return The corresponding OrderResponseDto.
     */
    public OrderResponseDto convertToOrderResponseDto(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }

    /**
     * Converts an {@link OrderItemRequestDto} to an {@link OrderItem} entity.
     * Retrieves the associated {@link Product} entity from the repository using the provided product ID.
     *
     * @param orderItemRequestDto The DTO containing order item request details.
     * @return The corresponding OrderItem entity.
     * @throws BadRequestException if the product with the given ID is not found.
     */
    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {
        return OrderItem.builder()
                .quantity(orderItemRequestDto.getQuantity())
                .product(productRepository.findById(orderItemRequestDto.getProductId())
                        .orElseThrow(() -> new BadRequestException("Requested product was not found")))
                .build();
    }

    /**
     * Converts a {@link ProductRequestDto} to a {@link Product} entity.
     *
     * @param productRequestDto The DTO containing product request details.
     * @return The corresponding Product entity.
     */
    public Product convertToProduct(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto, Product.class);
    }

    /**
     * Converts a {@link Product} entity to a {@link ProductResponseDto}.
     *
     * @param product The Product entity to be converted.
     * @return The corresponding ProductResponseDto.
     */
    public ProductResponseDto convertToProductResponseDto(Product product) {
        modelMapper.typeMap(Product.class, ProductResponseDto.class);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    /**
     * Converts a {@link Category} entity to a {@link CategoryResponseDto}.
     *
     * @param category The Category entity to be converted.
     * @return The corresponding CategoryResponseDto.
     */
    public CategoryResponseDto convertToCategoryResponseDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    /**
     * Converts a {@link CategoryRequestDto} to a {@link Category} entity.
     *
     * @param categoryRequestDto The DTO containing category request details.
     * @return The corresponding Category entity.
     */
    public Category convertToCategory(CategoryRequestDto categoryRequestDto) {
        return modelMapper.map(categoryRequestDto, Category.class);
    }
}
