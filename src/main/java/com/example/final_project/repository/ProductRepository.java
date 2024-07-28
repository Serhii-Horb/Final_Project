package com.example.final_project.repository;


import com.example.final_project.entity.Product;
import org.springframework.data.domain.Sort;


import com.example.final_project.entity.query.ProductCountInterface;
import com.example.final_project.entity.query.ProductPendingInterface;
import com.example.final_project.entity.query.ProductProfitInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("DELETE FROM Product product " +
            "WHERE product.productId = :id")
    void deleteById(Long id);


    @Query("SELECT product FROM Product product " +
            "WHERE product.discountPrice IS NOT NULL " +
            "AND (SELECT MAX(product.price / product.discountPrice) " +
            "FROM Product product) = (product.price / product.discountPrice)")
    List<Product> getMaxDiscountProduct();


    @Query(value =
            "SELECT  p.ProductID as productId, p.Name as name, o.Status as status, SUM(Quantity) as count, SUM(Quantity*Price) as sum " +
                    "FROM Products p JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
                    "JOIN Orders o ON oi.OrderId = o.OrderID " +
                    "WHERE   "+
                    "(:status ='PAID' AND o.Status IN ('PAID','ON_THE_WAY','DELIVERED') ) OR " +
                    "(:status = 'CANCELED' AND o.Status = 'CANCELED' )" +
                    "GROUP BY p.ProductID, p.Name, o.Status " +
                    "ORDER BY Count DESC  " +
                    "LIMIT 10"
            , nativeQuery = true
    )
    List<ProductCountInterface> findTop10Products(String status);


    @Query("SELECT product from Product product " +
            "WHERE (:hasCategory = FALSE OR product.category.categoryId = :category) " +
            "AND product.price BETWEEN :minPrice and :maxPrice " +
            "AND (:hasDiscount = FALSE OR product.discountPrice IS NOT NULL)")
    List<Product> findProductsByFilter(Boolean hasCategory, Long category, BigDecimal minPrice, BigDecimal maxPrice, Boolean hasDiscount, Sort sortObject);



    @Query (value =
            "SELECT \n" +
                    "    p.ProductID AS productId,\n" +
                    "    p.Name AS name,\n" +
                    "    oi.Quantity AS count,\n" +
                    "    o.Status AS status\n" +
                    "FROM \n" +
                    "    Orders o\n" +
                    "JOIN \n" +
                    "    OrderItems oi ON o.OrderID = oi.OrderID\n" +
                    "JOIN \n" +
                    "    Products p ON oi.ProductID = p.ProductID\n" +
                    "WHERE \n" +
                    "    o.Status = 'AWAITING_PAYMENT'\n" +
                    "    AND o.CreatedAt >= DATE_SUB(CURRENT_DATE, INTERVAL :day DAY);\n ", nativeQuery = true)
    List<ProductPendingInterface> findProductPending(Integer day);

// тест проходит, но не работает приложение из-за h2
//    @Query(value =
//            "SELECT \n" +
//                    "    p.ProductID AS productId,\n" +
//                    "    p.Name AS name,\n" +
//                    "    oi.Quantity AS count,\n" +
//                    "    o.Status AS status\n" +
//                    "FROM \n" +
//                    "    Orders o\n" +
//                    "JOIN \n" +
//                    "    OrderItems oi ON o.OrderID = oi.OrderID\n" +
//                    "JOIN \n" +
//                    "    Products p ON oi.ProductID = p.ProductID\n" +
//                    "WHERE \n" +
//                    "    o.Status = 'AWAITING_PAYMENT'\n" +
//                    "    AND o.CreatedAt >= DATEADD('DAY', -:day, CURRENT_DATE);\n", nativeQuery = true)
//    List<ProductPendingInterface> findProductPending(Integer day);





    @Query(value =
            "SELECT " +
                    "CASE " +
                   // "    WHEN :period = 'HOUR' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m-%d %H:00:00') " +
                    "    WHEN :period = 'DAY' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') " +
                    "    WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%x-%v') " + // %x-%v для года и недели
                    "    WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') " +
                    "END AS period, " +
                    "SUM(oi.Quantity * oi.PriceAtPurchase) AS sum " +
                    "FROM Orders o " +
                    "JOIN OrderItems oi ON o.OrderID = oi.OrderID " +
                    "WHERE o.Status = 'DELIVERED' AND o.CreatedAt >= " +
                    "CASE " +
                   // "    WHEN :period = 'HOUR' THEN DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :value HOUR) " +
                    "    WHEN :period = 'DAY' THEN DATE_SUB(CURRENT_DATE, INTERVAL :value DAY) " +
                    "    WHEN :period = 'WEEK' THEN DATE_SUB(CURRENT_DATE, INTERVAL :value WEEK) " +
                    "    WHEN :period = 'MONTH' THEN DATE_SUB(CURRENT_DATE, INTERVAL :value MONTH) " +
                    "END " +
                    "GROUP BY " +
                    "CASE " +
                  //  "    WHEN :period = 'HOUR' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m-%d %H:00:00') " +
                    "    WHEN :period = 'DAY' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') " +
                    "    WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%x-%v') " + // %x-%v для года и недели
                    "    WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') " +
                    "END",
            nativeQuery = true)
    List<ProductProfitInterface> findProfitByPeriod(String period, Integer value);
}