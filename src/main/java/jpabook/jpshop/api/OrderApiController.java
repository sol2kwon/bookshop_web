package jpabook.jpshop.api;

import jpabook.jpshop.domain.Order;
import jpabook.jpshop.domain.OrderItem;
import jpabook.jpshop.repository.OrderRepository;
import jpabook.jpshop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/vi/oders")

    public List<Order> odersV1(){
        List<Order> all = orderRepository. findAllByString(new OrderSearch());
        for (Order order : all){
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

}
