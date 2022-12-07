package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품등록
    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    // 상품조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 상품목록조회
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
