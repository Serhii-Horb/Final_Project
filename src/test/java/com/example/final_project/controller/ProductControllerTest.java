package com.example.final_project.controller;

import com.example.final_project.dto.ProductCountDto;
import com.example.final_project.dto.ProductPendingDto;
import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.security.config.SecurityConfig;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productServiceMock;

    @MockBean
    private JwtProvider jwtProvider;

    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test Product");
        productRequestDto.setDescription("Description");
        productRequestDto.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4e/Curimata_cf_cyprinoides.jpg/800px-Curimata_cf_cyprinoides.jpg");
        productRequestDto.setPrice(BigDecimal.valueOf(100));
        productRequestDto.setDiscountPrice(BigDecimal.valueOf(90));
        productRequestDto.setCategoryId(1L);

        productResponseDto = new ProductResponseDto();
        productResponseDto.setProductId(1L);
        productResponseDto.setName("Test Product");
        productResponseDto.setDescription("Description");
        productResponseDto.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4e/Curimata_cf_cyprinoides.jpg/800px-Curimata_cf_cyprinoides.jpg");
        productResponseDto.setPrice(BigDecimal.valueOf(100));
        productResponseDto.setDiscountPrice(BigDecimal.valueOf(90));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testGetAllProducts() throws Exception {
        when(productServiceMock.getAllProducts()).thenReturn(Collections.singletonList(productResponseDto));

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(productResponseDto.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testGetProductById() throws Exception {
        when(productServiceMock.getProductById(1L)).thenReturn(productResponseDto);

        mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productResponseDto.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testDeleteProductById() throws Exception {
        doNothing().when(productServiceMock).deleteProductById(1L);

        mockMvc.perform(delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testInsertProduct() throws Exception {
        doNothing().when(productServiceMock).insertProduct(any(ProductRequestDto.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testUpdateProduct() throws Exception {
        doNothing().when(productServiceMock).updateProduct(any(ProductRequestDto.class), eq(1L), any());

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testGetTop10Products() throws Exception {
        ProductCountDto productCountDto = new ProductCountDto();
        productCountDto.setProductId(1L);
        productCountDto.setName("Test Product");
        productCountDto.setCount(10);

        when(productServiceMock.getTop10Products(null)).thenReturn(Collections.singletonList(productCountDto));

        mockMvc.perform(get("/products/top10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testGetProductPending() throws Exception {
        ProductPendingDto productPendingDto = new ProductPendingDto();
        productPendingDto.setProductId(1L);
        productPendingDto.setName("Test Product");

        when(productServiceMock.findProductPending(5)).thenReturn(Collections.singletonList(productPendingDto));

        mockMvc.perform(get("/products/pending")
                        .param("day", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }
}


