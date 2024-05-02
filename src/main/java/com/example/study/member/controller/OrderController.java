package com.example.study.member.controller;

import com.example.study.member.domain.Member;
import com.example.study.member.domain.Order;
import com.example.study.member.domain.item.Item;
import com.example.study.member.repository.OrderSearch;
import com.example.study.member.service.ItemService;
import com.example.study.member.service.MemberService;
import com.example.study.member.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.orderSave(memberId,itemId,count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String OrderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId")Long orderId){
        orderService.cacelOrder(orderId);
        return "redirect:/orders";
    }

}
