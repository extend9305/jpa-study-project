package com.example.study.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemDto {
    private Long id;
    private String name;
    private int stockQuantity;
    private String author;
    private int price;
    private String isbn;
}
