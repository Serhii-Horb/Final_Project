package com.example.final_project.repository;

import com.example.final_project.entity.Product;
import com.example.final_project.entity.query.ProductCountInterface;
import com.example.final_project.entity.query.ProductPendingInterface;
import com.example.final_project.entity.query.ProductProfitInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

//    @Autowired
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void testDeleteById() {
//        Product product = new Product();
//        product.setProductId(1L);
//        productRepository.save(product);
//        productRepository.deleteById(1L);
//        assertThat(productRepository.findById(1L)).isEmpty();
//    }
//
//    @Test
//    void testGetMaxDiscountProduct() {
//        Product product1 = new Product();
//        product1.setProductId(1L);
//        product1.setPrice(new BigDecimal("100.00"));
//        product1.setDiscountPrice(new BigDecimal("50.00"));
//        productRepository.save(product1);
//
//        Product product2 = new Product();
//        product2.setProductId(2L);
//        product2.setPrice(new BigDecimal("200.00"));
//        product2.setDiscountPrice(new BigDecimal("100.00"));
//        productRepository.save(product2);
//        List<Product> maxDiscountProducts = productRepository.getMaxDiscountProduct();
//        assertThat(maxDiscountProducts).hasSize(2);     //?????????????
//        assertThat(maxDiscountProducts.get(0).getProductId()).isEqualTo(1L);
//    }
//
//    @Test
//    void testFindTop10Products() {
//        List<ProductCountInterface> top10Products = productRepository.findTop10Products("PAID");
//        assertThat(top10Products).isNotEmpty();
//    }
//
//    @Test
//    void testFindProductsByFilter() {
//        Product product1 = new Product();
//        product1.setProductId(1L);
//        product1.setPrice(new BigDecimal("100.00"));
//        product1.setDiscountPrice(new BigDecimal("50.00"));
//        productRepository.save(product1);
//
//        List<Product> filteredProducts = productRepository.findProductsByFilter(false, null, new BigDecimal("50.00"), new BigDecimal("150.00"), false, Sort.by("price"));
//
//        assertThat(filteredProducts).hasSize(1);
//        assertThat(filteredProducts.get(0).getProductId()).isEqualTo(1L);
//    }
//
//    @Test
//    void testFindProductPending() {
//        List<ProductPendingInterface> pendingProducts = productRepository.findProductPending(30);
//        assertThat(pendingProducts).isNotEmpty();
//    }
//
//    @Test
//    void testFindProfitByPeriod() {
//        List<ProductProfitInterface> profitByPeriod = productRepository.findProfitByPeriod("MONTH", 1);
//        assertThat(profitByPeriod).isNotEmpty();
//    }
}
