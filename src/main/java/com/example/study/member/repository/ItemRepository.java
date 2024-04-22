package com.example.study.member.repository;

import com.example.study.member.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager entityManager;

    public void save(Item item){
        if(item.getId() == null){
            entityManager.persist(item);
        }{
            entityManager.merge(item);
        }
    }

    public Item findOne(Long id){
        return entityManager.find(Item.class,id);
    }
    public List<Item> findByAll(){
        return entityManager.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

}
