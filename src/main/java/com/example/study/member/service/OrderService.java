package com.example.study.member.service;

import com.example.study.member.domain.Delivery;
import com.example.study.member.domain.Member;
import com.example.study.member.domain.Order;
import com.example.study.member.domain.OrderItem;
import com.example.study.member.domain.item.Item;
import com.example.study.member.repository.ItemRepository;
import com.example.study.member.repository.MemberRepository;
import com.example.study.member.repository.OrderRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositroy orderRepositroy;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long orderSave(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member,delivery,orderItem);

        orderRepositroy.save(order);
        return order.getId();
    }
    //취소
    @Transactional
    public void cacelOrder(Long orderId){
        Order order = orderRepositroy.findByOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepositroy.findAll(orderSearch);
//    }
}
