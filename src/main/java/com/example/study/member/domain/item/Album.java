package com.example.study.member.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@DiscriminatorValue("A")
public class Album extends Item{
    private String artist;
    private String etc;
}
