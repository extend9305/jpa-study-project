package com.example.study.member.service;

import com.example.study.member.domain.Address;
import com.example.study.member.domain.Member;
import com.example.study.member.domain.Order;
import com.example.study.member.domain.OrderStatus;
import com.example.study.member.domain.item.Book;
import com.example.study.member.exception.NotEnoughStockException;
import com.example.study.member.repository.OrderRepositroy;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepositroy orderRepositroy;
    @Test
    public void 상품주문(){
        Member member = getMember();

        Book book = getBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.orderSave(member.getId(),book.getId(),orderCount);

        Order order = orderRepositroy.findByOne(orderId);
        Assertions.assertEquals( OrderStatus.ORDER,order.getStatus(),"상품 주문시 상태는 ORDER");
        Assertions.assertEquals( 1,order.getOrderItems().size(),"주문한 상품 종류 수가 정확해야한다.");
        Assertions.assertEquals( 10000 * orderCount,order.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        Assertions.assertEquals( 8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");

    }

    private Book getBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소(){
        Member member = getMember();
        Book item = getBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.orderSave(member.getId(), item.getId(), orderCount);

        orderService.cacelOrder(orderId);

        Order cancelOrder = orderRepositroy.findByOne(orderId);

        assertEquals(OrderStatus.CANCEL,cancelOrder.getStatus(),"주문 취소시 상태는 CANCEL 이어야 한다.");
        assertEquals(10,item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야한다.");
    }
    @Test
    public void 상품주문_재고수량초과(){
        Member member = getMember();

        Book book = getBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class,()->{
            orderService.orderSave(member.getId(),book.getId(),orderCount);
        });

    }

}