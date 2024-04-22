package com.example.study.member.repository;

import com.example.study.member.domain.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositroy {
    private final EntityManager entityManager;

    public void save(Order order){
        entityManager.persist(order);
    }
    public Order findByOne(Long id){
        return entityManager.find(Order.class,id);
    }
//    public List<Order> findAll(OrderSearch orderSearch){}
}
