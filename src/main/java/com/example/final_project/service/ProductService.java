package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.ProductCountDto;
import com.example.final_project.dto.ProductPendingDto;
import com.example.final_project.dto.ProductProfitDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final Mappers mappers;
    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;

    public List<ProductResponseDto> getAllProducts() {
        logger.info("Fetching all products.");
        List<Product> productsList = productRepository.findAll();
        return MapperUtil.convertList(productsList, mappers::convertToProductResponseDto);
    }

    public ProductResponseDto getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return mappers.convertToProductResponseDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundInDbException("Incorrect id of product.")));
    }

    public void deleteProductById(Long id) {
        logger.info("Deleting product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        productRepository.delete(product);
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        logger.info("Inserting new product: {}", productRequestDto.getName());
        try {
            Product newProduct = mappers.convertToProduct(productRequestDto);
            productRepository.save(newProduct);
        } catch (Exception e) {
            logger.error("Error inserting product: {}", e.getMessage());
            throw new BadRequestException("Bad Request");
        }
    }

    public void updateProduct(ProductRequestDto productRequestDto, Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors while updating product with ID: {}", id);
            throw new BadRequestException("Bad Request");
        }

        logger.info("Updating product with ID: {}", id);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
            Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                    .orElseThrow(() -> new NotFoundInDbException("Incorrect id of category."));

            product.setName(productRequestDto.getName());
            product.setDescription(productRequestDto.getDescription());
            product.setImageURL(productRequestDto.getImageURL());
            product.setPrice(productRequestDto.getPrice());
            product.setCategory(category);
            product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            product.setDiscountPrice(productRequestDto.getDiscountPrice());

            productRepository.save(product);
        } catch (NotFoundInDbException e) {
            logger.error("Error updating product: {}", e.getMessage());
            throw new NotFoundInDbException("Entity not found: " + e.getMessage());
        }
    }

    public List<ProductCountDto> getTop10Products(String status) {
        logger.info("Fetching top 10 products with status: {}", status);
        return mapperUtil.convertList(productRepository.findTop10Products(status), mappers::convertToProductCountDto);
    }

    @Transactional
    public List<ProductResponseDto> findProductsByFilter(Long category, BigDecimal minPrice, BigDecimal maxPrice, Boolean hasDiscount, String sort) {
        logger.info("Filtering products with category: {}, minPrice: {}, maxPrice: {}, hasDiscount: {}, sort: {}", category, minPrice, maxPrice, hasDiscount, sort);
        boolean ascending = true;
        Sort sortObject = orderBy("name", true); // default sort
        boolean hasCategory = false;

        if (category != null) { hasCategory = true; }
        if (minPrice == null) { minPrice = BigDecimal.valueOf(0.00); }
        if (maxPrice == null) { maxPrice = BigDecimal.valueOf(Double.MAX_VALUE); }
        if (sort != null) {
            String[] sortArray = sort.split(",");
            if (sortArray[1].equals("desc")) {
                ascending = false;
            }
            sortObject = orderBy(sortArray[0], ascending);
        }
        return mapperUtil.convertList(productRepository.findProductsByFilter(hasCategory, category, minPrice, maxPrice, hasDiscount, sortObject), mappers::convertToProductResponseDto);
    }

    public List<ProductPendingDto> findProductPending(Integer day) {
        logger.info("Fetching pending products older than {} days", day);
        return mapperUtil.convertList(productRepository.findProductPending(day), mappers::convertToProductPendingDto);
    }

    public List<ProductProfitDto> findProductProfit(String period, Integer value) {
        logger.info("Fetching product profit for period: {}, value: {}", period, value);
        return mapperUtil.convertList(productRepository.findProfitByPeriod(period, value), mappers::convertToProductProfitDto);
    }

    private Sort orderBy(String sort, Boolean ascending) {
        logger.info("Sorting products by: {}, ascending: {}", sort, ascending);
        if (!ascending) {
            return Sort.by(Sort.Direction.DESC, sort);
        } else {
            return Sort.by(Sort.Direction.ASC, sort);
        }
    }
}
