package com.example.final_project.entity.query;

import java.sql.Timestamp;

public interface ProductPendingInterface {
    Long getProductId();
    String getName();
    Integer getCount();
    String getStatus();
}
