package com.example.final_project.service;

import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Favorite;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.exceptions.NoUsersFoundException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.FavoriteRepository;
import com.example.final_project.repository.ProductRepository;
import com.example.final_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {
    @Mock
    private FavoriteRepository favoriteRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private FavoriteService favoriteServiceMock;

    private User user;
    private Product product;
    private Favorite favorite;

    @BeforeEach
    public void setUp() {
        user = new User(1L,
                "Test User",
                "test.user@example.com",
                "+1234567777",
                "testpassword100",
                "",
                Role.USER,
                null,
                null,
                null);

        product = new Product(1L,
                "New Garden Hoe",
                "Sturdy garden hoe for weeding",
                new BigDecimal("20.00"),
                new Category(1L, "New category", null),
                "http://images/garden_hoe.jpg",
                new BigDecimal("18.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                null,
                null);

        favorite = new Favorite(1L, user, product);
    }

    @Test
    void testAddFavoriteByUserId_Success() {
        Long userId = 1L;
        Long productId = 1L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));
        when(favoriteRepositoryMock.save(any(Favorite.class))).thenReturn(favorite);

        assertDoesNotThrow(() -> favoriteServiceMock.addFavoriteByUserId(userId, productId));

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(productRepositoryMock, times(1)).findById(productId);
        verify(favoriteRepositoryMock, times(1)).save(any(Favorite.class));
    }

    @Test
    void testAddFavoriteByUserId_UserNotFound() {
        Long userId = 100L; // Несуществующий userId
        Long productId = 1L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        NoUsersFoundException exception = assertThrows(NoUsersFoundException.class,
                () -> favoriteServiceMock.addFavoriteByUserId(userId, productId));
        assertEquals("Incorrect id of user.", exception.getMessage());

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(productRepositoryMock, times(0)).findById(anyLong());
        verify(favoriteRepositoryMock, times(0)).save(any(Favorite.class));
    }

    @Test
    void testAddFavoriteByUserId_ProductNotFound() {
        Long userId = 1L;
        Long productId = 100L; // Несуществующий productId

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        NotFoundInDbException exception = assertThrows(NotFoundInDbException.class,
                () -> favoriteServiceMock.addFavoriteByUserId(userId, productId));
        assertEquals("Incorrect id of product.", exception.getMessage());

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(productRepositoryMock, times(1)).findById(productId);
        verify(favoriteRepositoryMock, times(0)).save(any(Favorite.class));
    }

    @Test
    void testGetAllFavoritesByUserId() {
        Long userId = 1L;
        List<Favorite> favoriteList = new ArrayList<>();
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(favoriteRepositoryMock.findByUser_UserId(userId)).thenReturn(favoriteList);

        List<FavoriteResponseDto> result = favoriteServiceMock.getAllFavoritesByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(favoriteRepositoryMock, times(1)).findByUser_UserId(userId);
        verify(mappersMock, times(0)).convertToProductResponseDto(any(Product.class));
        verify(mappersMock, times(0)).convertToFavoriteResponseDto(any(Favorite.class));
    }

    @Test
    void testDeleteFavoriteByUserId_FavoriteNotFound() {
        Long userId = 1L;
        Long productId = 100L; // Несуществующий productId

        when(favoriteRepositoryMock.findByUser_UserIdAndProduct_ProductId(userId, productId))
                .thenReturn(Optional.empty());

        NotFoundInDbException exception = assertThrows(NotFoundInDbException.class,
                () -> favoriteServiceMock.deleteFavoriteByUserId(userId, productId));
        assertEquals("Favorite not found.", exception.getMessage());

        verify(favoriteRepositoryMock, times(1)).findByUser_UserIdAndProduct_ProductId(userId, productId);
        verify(favoriteRepositoryMock, times(0)).delete(any(Favorite.class));
    }
}