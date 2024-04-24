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
    public List<Order> findAll(OrderSearch orderSearch){
        List<Order> orders = entityManager.createQuery("select o from Order o join o.member m " +
                "where o.status =:status " +
                "and m.name like :name",Order.class)
                .setParameter("status",orderSearch.getOrderStatus())
                .setParameter("name",orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
        return orders;
    }
}
