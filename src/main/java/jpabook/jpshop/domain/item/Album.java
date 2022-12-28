package jpabook.jpshop.domain.item;

import jpabook.jpshop.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //싱글 테이블이라 구분 타입
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
