package com.example.final_project.repository;

import com.example.final_project.dto.ProductCountDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.OrderItem;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.entity.query.ProductCountInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ProductRepository.class))
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    private Product product1, product2;
    private Order order1;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductId(1L);
        product1.setName("Product 1");
        product1.setPrice(new BigDecimal("149.00"));
        product1.setDiscountPrice(new BigDecimal("80.00"));
        productRepository.save(product1);

        product2 = new Product();
        product2.setProductId(2L);
        product2.setName("Product 2");
        product2.setPrice(new BigDecimal("200.00"));
        productRepository.save(product2);

        order1 = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order1);
        orderItem.setProduct(product1);
        orderItem.setQuantity(1);
        order1.setItems(List.of(orderItem));
        order1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderRepository.save(order1);
        orderItemRepository.save(orderItem);
    }

    @Test
    void testDeleteById() {
        productRepository.deleteById(1L);
        assertThat(productRepository.findById(1L)).isEmpty();
    }

    @Test
    void testGetMaxDiscountProduct() {
        List<Product> maxDiscountProducts = productRepository.getMaxDiscountProduct();
        assertThat(maxDiscountProducts).hasSize(1);
        assertThat(maxDiscountProducts.get(0).getProductId()).isEqualTo(1L);
    }

    @Test
    void testFindTop10Products() {
        order1.setStatus(Status.PAID);
        ProductCountDto productCountDto = new ProductCountDto(1L, "Product 1", 1, new BigDecimal(149.00));
        List<ProductCountInterface> top10Products = productRepository.findTop10Products("PAID");
        assertNotNull(top10Products);
        assertThat(top10Products.contains(productCountDto));
    }

    @Test
    void testFindProductsByFilter() {
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        List<Product> filteredProducts = productRepository.findProductsByFilter(false, null, new BigDecimal("50"), new BigDecimal("150"), true, sort);
        assertNotNull(filteredProducts);
        assertThat(filteredProducts.contains(product1));
    }

//    @Test
//    void testFindProductPending() {
//        order1.setStatus(Status.AWAITING_PAYMENT);
//        //ProductPendingDto productPendingDto = new ProductPendingDto(1L, "Product 1", 1, "Awaiting payment");
//        List<ProductPendingInterface> pendingProducts = productRepository.findProductPending(7);
//        assertNotNull(pendingProducts);
//    }

//    @Test
//    void testFindProfitByPeriod() {
//        List<ProductProfitInterface> profitByPeriod = productRepository.findProfitByPeriod("DAY", 7);
//
//    }
}

