package com.example.study.member.service;



import com.example.study.member.domain.item.Book;
import com.example.study.member.domain.item.Item;
import com.example.study.member.dto.UpdateItemDto;
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

    // 변경감지 기능
    @Transactional
    public void updateItem(Long itemId , UpdateItemDto bookDto){
        Book findOne = (Book) itemRepository.findOne(itemId);
        // findOne 으로 준영속 상태를 영속상태로 가져오고
        findOne.setName(bookDto.getName());
        findOne.setPrice(bookDto.getPrice());
        findOne.setStockQuantity(bookDto.getStockQuantity());
        findOne.setIsbn(bookDto.getIsbn());
        findOne.setAuthor(bookDto.getAuthor());
        //트랜잭션 끝나면 flush 된다.
    }

    public List<Item> findItems(){
        return itemRepository.findByAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
