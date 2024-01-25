package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @GetMapping
    public void get_payment_view(Model model, List<Integer> cartNo) {

    }

    @PostMapping
    public void process_payment() {

    }
}
