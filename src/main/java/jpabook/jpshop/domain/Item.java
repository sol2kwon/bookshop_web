package jpabook.jpshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //한 테이블에 다 때려박음
@DiscriminatorColumn(name = "dtype") //싱글 테이블이라 구분하겠다.
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==
    // setter로 외부에서 불러와서 값을 변경하는 것이 아니라, 핵심 로직을 이곳에서 해결한다. 응집도가 높음//
    /**
     * stock 증가(재고 증가)
     * */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소(재고 감소)
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0 ){
            throw new NotEnoughStockExeption("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
