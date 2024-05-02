package com.example.study.member.controller;

import com.example.study.member.domain.item.Book;
import com.example.study.member.domain.item.Item;
import com.example.study.member.dto.UpdateItemDto;
import com.example.study.member.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId , Model model){
        Book book = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(book.getId());
        bookForm.setName(book.getName());
        bookForm.setStockQuantity(book.getStockQuantity());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setPrice(book.getPrice());
        bookForm.setIsbn(book.getIsbn());

        model.addAttribute("form",bookForm);

        return "items/updateItemForm";
    }
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute BookForm form){

        UpdateItemDto bookDto = new UpdateItemDto();
        bookDto.setId(form.getId());
        bookDto.setName(form.getName());
        bookDto.setPrice(form.getPrice());
        bookDto.setStockQuantity(form.getStockQuantity());
        bookDto.setAuthor(form.getAuthor());
        bookDto.setIsbn(form.getIsbn());

        itemService.updateItem(form.getId(),bookDto);
        return "redirect:/items";
    }

}
