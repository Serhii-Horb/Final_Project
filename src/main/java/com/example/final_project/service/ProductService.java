package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Product;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Mappers mappers;
    private final CategoryRepository categoryRepository;

    public List<ProductResponseDto> getAllProducts() {
        List<Product> productsList = productRepository.findAll();
        return MapperUtil.convertList(productsList, mappers::convertToProductResponseDto);
    }

    public ProductResponseDto getProductById(Long id) {
        Optional<Product> productsOptional = productRepository.findById(id);
        ProductResponseDto productResponseDto = null;
        if (productsOptional.isPresent()) {
            productResponseDto = productsOptional.map(mappers::convertToProductResponseDto).orElse(null);
        }
        return productResponseDto;
    }

    public void deleteProductById(Long id) {
      Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
      productRepository.delete(product);
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getCategoryId() != null) {
            Product newProduct = mappers.convertToProduct(productRequestDto);
            newProduct.setProductId(0L);
            Product savedProducts = productRepository.save(newProduct);
        }
    }


    public void updateProduct(ProductRequestDto productRequestDto, Long id) {
        if (id > 0) {
            Optional<Product> productsOptional = productRepository.findById(id);
            if (!productsOptional.isPresent()) {
                throw new RuntimeException("Product not found with id: " + id);
            } else {
                Product product = productsOptional.get();
                product.setName(productRequestDto.getName());
                product.setDescription(productRequestDto.getDescription());
                product.setImageURL(productRequestDto.getImageURL());
                product.setPrice(productRequestDto.getPrice());
                Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(NoSuchFieldError :: new);
                product.setCategory(category);
                product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productRepository.save(product);
                product.setDiscountPrice(productRequestDto.getDiscountPrice());
            }
        }
    }
}
