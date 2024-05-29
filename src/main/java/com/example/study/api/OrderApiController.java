package com.example.study.api;

import com.example.study.member.domain.Address;
import com.example.study.member.domain.Order;
import com.example.study.member.domain.OrderItem;
import com.example.study.member.domain.OrderStatus;
import com.example.study.member.repository.OrderRepositroy;
import com.example.study.member.repository.OrderSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepositroy orderRepositroy;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepositroy.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        List<Order> all = orderRepositroy.findAll(new OrderSearch());
        List<OrderDto> collect = all.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        List<Order> all = orderRepositroy.finAllWithItem();
        List<OrderDto> collect = all.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                       @RequestParam(value = "offset",defaultValue = "100") int limit){
        List<Order> all = orderRepositroy.findAllWithMemberDelirvery(offset,limit);
        List<OrderDto> collect = all.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return collect;
    }


    @Getter
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order o){
            orderId = o.getId();
            name = o.getMember().getName();
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress();
            orderItems = o.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }

    }
    @Getter
    static class OrderItemDto{
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
