package com.example.study.member.domain.item;

import com.example.study.member.domain.Category;
import com.example.study.member.domain.OrderItem;
import com.example.study.member.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @OneToMany(mappedBy = "item")
    List<OrderItem> orderItems = new ArrayList<>();
    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();

    //===비즈니스 로직==//

    /**
     * stock 증가
     * @param quantity
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity){
        int resStock = this.stockQuantity - quantity;
        if(resStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
    }
}
