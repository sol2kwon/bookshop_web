package jpabook.jpshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded //어딘가에 내장 되었다.
    private Address address;


    @Enumerated(EnumType.STRING) // EnumType.ORDINAL 사용하지말기.
    private DeliveryStatus status;//READY 배송준비, COMP 배송중
}
