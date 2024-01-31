package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String shop() {
        return "redirect:/shop";
    }

    @GetMapping("/shop")
    public String main_page(Model model) {

        return "main/shop";
    }

    @GetMapping("/test")
    public String test_page() {
        return "test";
    }



}
