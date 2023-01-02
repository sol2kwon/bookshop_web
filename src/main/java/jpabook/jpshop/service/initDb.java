package jpabook.jpshop.service;

import jpabook.jpshop.domain.*;
import jpabook.jpshop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 성능최적화를 위한 샘플 데이터입니다.
 * */

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInti1();
        initService.dbInti2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInti1(){
            Member member = createMember("userA", "서울", "1", "111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000,100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000,200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,10000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,20000,2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);
    }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInti2(){
            Member member = createMember("userB", "부산", "2", "222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000,100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000,200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,20000,2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,40000,3);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);
        }

        private Book createBook(String name,int price, int stokQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stokQuantity);
            return book1;
        }
    }
}
