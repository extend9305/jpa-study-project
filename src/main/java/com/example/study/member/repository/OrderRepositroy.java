package com.example.study.member.repository;

import com.example.study.member.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelirvery(){
        return entityManager.createQuery(
                "select o from  Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d",Order.class)
                .getResultList();
    }


    public List<Order> finAllWithItem() {
        return entityManager.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i"
                        ,Order.class)
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();

    }

    public List<Order> findAllWithMemberDelirvery(int offset, int limit) {
        return entityManager.createQuery(
                "select o from  Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d",Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
