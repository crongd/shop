package com.shop.service;

import com.shop.dto.product.ProductDTO;
import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.OrderMapper;
import com.shop.mappers.ShoppingCartMapper;
import com.shop.mappers.UserMapper;
import com.shop.mappers.WishListMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final String NAVER_SMS_SEND_URL = "https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/messages";

    private final UserMapper userMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestOperations restOperations;
    private final OrderMapper orderMapper;
    private final WishListMapper wishListMapper;


    // 회원가입
    public void join_user(UserDTO userDTO) {
        // encoding
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCi(UUID.randomUUID().toString()); // 개발시 테스트용 ci 배포시 제거
        // db 밀어
        userMapper.join_user(userDTO);
    }



    // 장바구니에 상품 담기
    @Transactional
    public void add_product_in_shopping_cart(ShoppingCartDTO shoppingCartDTO){
        System.out.println("user service");
        System.out.println(shoppingCartDTO);
        shoppingCartMapper.insert_shopping_cart(shoppingCartDTO);
        shoppingCartMapper.insert_shopping_cart_option(shoppingCartDTO);
    }

//    public List<ProductDTO> get_shopping_cart_by_user(UserDTO userDTO) {
////        return shoppingCartMapper.get_shopping_cart_by_user(userDTO);
//    }


    // 유저의 장바구니 정보 가져오기
    public List<ShoppingCartDTO> get_shopping_cart_of_user(UserDTO userDTO) {
        return shoppingCartMapper.get_shopping_cart_by_user(userDTO, null);
    }

    // 장바구니 amount 수정
    public void update_shopping_cart_product_amount(ShoppingCartDTO shoppingCartDTO) {
        shoppingCartMapper.change_product_amount(shoppingCartDTO);
    }

    public void delete_product_in_shopping_cart(ShoppingCartDTO shoppingCartDTO) {
//        shoppingCartDTOS.forEach(shoppingCartDTO -> {
//            shoppingCartDTO.setUser(userDTO);
//        });

        shoppingCartMapper.delete_product(shoppingCartDTO);
    }



    // 마이페이지 본인 물건 조회
    public List<OrderDTO> get_my_page_data(UserDTO userDTO) {
        return orderMapper.get_orders(userDTO);
    }



    public List<ProductDTO> get_my_page_detail_data(UserDTO userDTO, String orderId) {
        return shoppingCartMapper.get_my_page_detail_data(userDTO, orderId);
    }



    // wish list
    public List<ProductDTO> get_wish_list(UserDTO userDTO) {
        return wishListMapper.wish_list_get(userDTO);
    }

    public void add_wish_list(UserDTO userDTO, int productNo) {
        wishListMapper.wish_list_add(userDTO, productNo);
    }

    public void delete_wish_list(UserDTO userDTO, int productNo) {
        wishListMapper.wish_list_delete(userDTO, productNo);
    }




}
