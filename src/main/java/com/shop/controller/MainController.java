package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    OrderMapper orderMapper;
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

    @GetMapping("/test2")
    @ResponseBody
    public String test(Model model) throws JsonProcessingException {
        List<Map<String, String>> results = orderMapper.test();
        System.out.println("reslts" + results);
        Map<String, String> resultMap = new HashMap<>();
        for(Map<String, String> value : results) {
            resultMap.put(value.get("id"), value.get("password"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(resultMap);
    }



}
