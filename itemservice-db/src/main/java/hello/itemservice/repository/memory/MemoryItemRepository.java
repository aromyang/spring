package hello.itemservice.repository.memory;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        return store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(itemName)) {
                        //itemName 조건이 없는 경우 전체 검색(true)
                        return true;
                    }
                    //itemName 값이 있는 경우 값을 포함한 결과만 검색
                    return item.getItemName().contains(itemName);
                }).filter(item -> {
                    if (maxPrice == null) {
                        //maxPrice 조건이 없는 경우 전체 검색
                        return true;
                    }
                    //maxPrice 값이 있는 경우 item의 price가 maxPrice보다 작은 결과 검색
                    return item.getPrice() <= maxPrice;
                })
                .collect(Collectors.toList());
    }

    //테스트 용도
    public void clearStore() {
        store.clear();
    }

}
