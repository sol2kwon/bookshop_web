package jpabook.jpshop.api;

import jpabook.jpshop.domain.Address;
import jpabook.jpshop.domain.Order;
import jpabook.jpshop.domain.OrderStatus;
import jpabook.jpshop.repository.OrderRepository;
import jpabook.jpshop.repository.OrderSearch;
import jpabook.jpshop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Oder -> Member
 * Oder -> Delivery
 * */

@RestController
@RequiredArgsConstructor
public class OderSimpleApiController {
    private final OrderRepository orderRepository;


    /**
     * ordersV1을 호출하면 어떻게 될까요?
     * 1. 양방향 관계로 무한루프에 빠지게 됩니다.
     * 대처방안 : 양방향 연관관계가 걸린 곳은 꼭! 한곳을 @JsonIgnore로 처리한다.
     * 2. proxy ByteBuddyInterceptor 오류를 만나게 됩니다.
     * 대처방안 : jackson-datatype-hibernate5 그래들 추가 후 @Bean 등록 (엔티티 외부 노출안하면 사용할 일도 없음..)
     * 3. 엔티티 직접 노출, 성능 낭비
     * 대처방안 : Dto로 반환하여 필요한 데이터만 처리한다.
     * 4. 지연로딩을 피하기위해 즉시로딩으로 설정하면 안됩니다.
     * 대처방안: 항상 지연로딩을 기본으로하고,성능최적화를 위해 패치조인을 사용한다.
     * 즉..엔티티 직접 노출 금지!
     * */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }


    /**
     * ordersV2을 호출하면 어떻게 될까요?
     * 1. 엔티티를 Dto로 변환하는 일반적인 방법이지만, 쿼리가 5번 실행되었다.
     * N+1문제 발생.. -> 1+ 회원 N + 배송 N = 5개
     * 대처방안: 패치조인을 사용한다.ordersV3 확인
     * */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<SimpleOrderDto> result = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return result;

    }

    /**
     * ordersV3을 호출하면 어떻게 될까요?
     * 1. 쿼리 한번으로 조회한다. 셀렉트절에서 다 가져온다.
     *  대처방안: 패치조인 사용한다. 재사용성 용이하다.
     * */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * ordersV4을 호출하면 어떻게 될까요?
     * 1. 쿼리 한번으로 조회한다. 셀렉트절에서 필요한 값만 가져온다.(Dto에서 직접 조회)
     * 대처방안: 재사용성이 없다, 직접 쿼리 짠거임.
     * */
    @GetMapping("/api/v/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
        
    }
}
