package com.example.study.member.repository;

import com.example.study.member.domain.Address;
import com.example.study.member.domain.Order;
import com.example.study.member.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName(); // Lazy 초기화
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); // Lazy 초기화
    }
}
