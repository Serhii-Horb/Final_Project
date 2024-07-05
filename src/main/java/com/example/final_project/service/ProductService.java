package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Product;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


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
        return mappers.convertToProductResponseDto(productRepository.findById(id).orElseThrow(()
                -> new NotFoundInDbException("Incorrect id of product.")));
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        productRepository.delete(product);

      Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
      productRepository.delete(product);
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        try {
            Product newProduct = mappers.convertToProduct(productRequestDto);
            newProduct.setProductId(0L);
            Product savedProducts = productRepository.save(newProduct);
        } catch (Exception e) {
            throw new BadRequestException("Bad Request");
        }
    }

    public void updateProduct(ProductRequestDto productRequestDto, Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(()
                -> new NotFoundInDbException("Incorrect id of category."));
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setImageURL(productRequestDto.getImageURL());
        product.setPrice(productRequestDto.getPrice());
        product.setCategory(category);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setDiscountPrice(productRequestDto.getDiscountPrice());
        productRepository.save(product);
    }
}