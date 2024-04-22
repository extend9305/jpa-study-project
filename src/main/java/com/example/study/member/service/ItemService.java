package com.example.study.member.service;



import com.example.study.member.domain.item.Item;
import com.example.study.member.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> findAll(){
        return itemRepository.findByAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
