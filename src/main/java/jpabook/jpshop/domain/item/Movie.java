package jpabook.jpshop.domain.item;

import jpabook.jpshop.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
