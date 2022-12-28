package jpabook.jpshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter //실무에서는 필요할때만 열어두기
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String username;
}
