package com.example.study;

import com.example.study.member.domain.*;
import com.example.study.member.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member1 = createMember("userA","대전","2","2");
            em.persist(member1);

            Book book1 = createBook("JPA1 BOOK",10000,100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK",20000,100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);


            Delivery delivery = getDelivery(member1);
            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Book createBook(String name, int price, int quantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(quantity);
            return book1;
        }

        public void dbInit2(){
            Member member2 = createMember("userB","서울","1","1");
            em.persist(member2);

            Book book1 = createBook("SPRING1 BOOK",20000,100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK",40000,100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);


            Delivery delivery = getDelivery(member2);
            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Member createMember(String name, String city, String street ,String zipCode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipCode));
            return member;
        }

    }
}

