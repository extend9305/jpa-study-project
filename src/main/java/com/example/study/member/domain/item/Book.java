package com.example.study.member.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;
}