package com.example.final_project.mapper;

import com.example.final_project.config.MapperUtil;
import com.example.final_project.dto.OrderDto;
import com.example.final_project.dto.OrderItemDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public OrderItem convertIntoOrderItem(OrderItemDto orderItemDto) {
        return modelMapper.map(orderItemDto, OrderItem.class);
    }
    public OrderItemDto convertIntoOrderItemDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDto.class);
    }
    public Order convertIntoOrder(OrderDto orderDto) {
        return Order.builder()
                .deliveryAddress(orderDto.getDeliveryAddress())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .items(MapperUtil.convertList(orderDto.getItems(),this::convertIntoOrderItem))
                .build();
    }
    public OrderDto convertIntoOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryMethod(order.getDeliveryMethod())
                .items(MapperUtil.convertList(order.getItems(),this::convertIntoOrderItemDto))
                .build();
    }

}
