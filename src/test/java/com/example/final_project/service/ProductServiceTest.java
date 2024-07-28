package com.example.final_project.service;


import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Product;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Mappers mappers;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsertProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Product 1");
        productRequestDto.setDescription("Description 1");
        productRequestDto.setPrice(new BigDecimal("100.00"));
        productRequestDto.setCategoryId(1L);
        productRequestDto.setImageURL("http://example.com/image1.png");
        productRequestDto.setDiscountPrice(new BigDecimal("90.00"));

        Product product = new Product();
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal("100.00"));
        product.setImageURL("http://example.com/image1.png");
        product.setDiscountPrice(new BigDecimal("90.00"));
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        when(mappers.convertToProduct(productRequestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        assertDoesNotThrow(() -> productService.insertProduct(productRequestDto));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Long categoryId = 1L;

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Updated Product");
        productRequestDto.setDescription("Updated Description");
        productRequestDto.setPrice(new BigDecimal("150.00"));
        productRequestDto.setCategoryId(categoryId);
        productRequestDto.setImageURL("http://example.com/image_updated.png");
        productRequestDto.setDiscountPrice(new BigDecimal("140.00"));

        Product product = new Product();
        product.setProductId(productId);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal("100.00"));
        product.setImageURL("http://example.com/image1.png");
        product.setDiscountPrice(new BigDecimal("90.00"));

        Category category = new Category();
        category.setCategoryId(categoryId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(bindingResult.hasErrors()).thenReturn(false);

        productService.updateProduct(productRequestDto, productId, bindingResult);

        assertEquals("Updated Product", product.getName());
        assertEquals("Updated Description", product.getDescription());
        assertEquals(new BigDecimal("150.00"), product.getPrice());
        assertEquals("http://example.com/image_updated.png", product.getImageURL());
        assertEquals(new BigDecimal("140.00"), product.getDiscountPrice());
        assertEquals(category, product.getCategory());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductWithErrors() {
        Long productId = 1L;
        ProductRequestDto productRequestDto = new ProductRequestDto();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(BadRequestException.class, () -> productService.updateProduct(productRequestDto, productId, bindingResult));
    }

    @Test
    public void testUpdateProductNotFound() {
        Long productId = 1L;
        Long categoryId = 1L;

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setCategoryId(categoryId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundInDbException.class, () -> productService.updateProduct(productRequestDto, productId, bindingResult));
    }

    @Test
    public void testUpdateProductCategoryNotFound() {
        Long productId = 1L;
        Long categoryId = 1L;

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setCategoryId(categoryId);

        Product product = new Product();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundInDbException.class, () -> productService.updateProduct(productRequestDto, productId, bindingResult));
    }
}

