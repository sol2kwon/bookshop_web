package jpabook.jpshop.service;

import jpabook.jpshop.domain.Item;
import jpabook.jpshop.domain.item.Book;
import jpabook.jpshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
/**
 * 변경감지 기능 사용
 * 엔티티를 변경할 시 꼭 변경감지 기능을 사용한다.(머지 안씀.)
 * tip : set set set 하지말고 의미있는 데이터를 도메인에 따로 만들어서 변경해주기..
 * */
    @Transactional
    public void updateItem(Long itemId,String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    /**
     * merge사용시 아래와 같음
     * 동작방식
     * 1. merge() 실행
     * 2. 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회한다.
     *   2-1. 1차 캐시에 엔티티가 없으면 데이터베이스에서 엔티티를 조회하고, 1차 캐시에 저장한다.
     * 3. 조회한 영속 엔티티에 파라미터로 넘어온 준영속 엔티티로 바꿔치기한다.
     * 4. 바꿔치기한 영속상태의 엔티티로 반환한다.
     * 주의점 : 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경 가능하지만, merge를 사용하면 모든 속성이 변경된다.
     *         병합시 값이 없으면 null로 업데이트 할 위험도 있다.(merge는 모든 필드를 교체한다.)

    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
    }
     * */

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
