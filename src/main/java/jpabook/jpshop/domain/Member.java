package jpabook.jpshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter //실무에서는 필요할때만 열어두기
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Embedded //어딘가에 내장 되었다.
    private Address address;

    @OneToMany(mappedBy = "member")//읽기전용
    private List<Order> orders = new ArrayList<>();


}
