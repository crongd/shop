package com.shop.controller;

import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.service.OrderService;
import com.shop.service.PortOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final PortOneService portOneService;

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
    public String post_order_of_cart(@RequestParam("no") List<Integer> cartNo, @AuthenticationPrincipal UserDTO userDTO, Model model) {
        System.out.println(userDTO);
//        orderService.create_order(cartNo, userDTO);
        List<ShoppingCartDTO> list = orderService.get_order_of_shopping_cart(cartNo, userDTO);
        System.out.println(list);
        model.addAttribute("cartList", list);

        return "main/pay-page";
    }


    //////////////////// 결제관련 /////////////////////////////

    // 결제 요청 전 결제 정보 PORTONE에 등록하기 (사전검증)
    @ResponseBody
    @PostMapping("/payment/pre-verification")
    public ResponseEntity<String> post_pre_verification(
            @RequestParam("merchant_uid") String merchant_uid,
            @RequestParam("amount") String amount
    ) {

        String message = portOneService.pre_verification_order(merchant_uid, amount);


        return Objects.isNull(message) ? ResponseEntity.status(HttpStatus.OK).body(message) : ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @ResponseBody
    @GetMapping("/payment/{imp_uid}")
    public ResponseEntity<String> get_inquery_order(
            @AuthenticationPrincipal UserDTO userDTO,
            @PathVariable("imp_uid") String imp_uid,
            @RequestParam("no") List<Integer> cartNumbers
    ) {
        Map<Boolean, ? extends Object> resultOb =  portOneService.get_inquery_order(imp_uid);
        boolean result = resultOb.keySet().iterator().next();

        if (result) {
            // db에 저장~
            Map response = (Map) resultOb.get(result);
            orderService.create_order(userDTO, response, cartNumbers);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        // 값 가져오기 실패
        String message = "";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

//        return result ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

//        return null;
    }











}
