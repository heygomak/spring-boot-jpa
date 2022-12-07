package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품등록
    public void save(Item item) {
        if(item.getId() != null) {
            em.persist(item); // 최초 저장하기 전까지는 새로운 객체를 생성하는 것
        } else {
            em.merge(item);  // 이미 DB에 등록된 것을 가져와서 update 한다고 보면됨
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
