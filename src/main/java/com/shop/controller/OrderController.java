package com.shop.controller;

import com.shop.dto.user.UserDTO;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public void get_order_items(
            @AuthenticationPrincipal UserDTO userDTO,
            @RequestParam("no") int orderNo,
            Model model
    ) {

        orderService.get_order(orderNo, userDTO);
    }

    // 장바구니에 있는 내용들을 주문/결제 목록(DB)에 넣고 주문/결제 창으로 이동
    @PostMapping
    public void post_order_of_cart(@RequestParam("no") List<Integer> cartNo, @AuthenticationPrincipal UserDTO userDTO, Model model) {
        System.out.println(userDTO);
        orderService.create_order(cartNo, userDTO);
        return;
    }
}
