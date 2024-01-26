package com.shop.service;

import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.OrderMapper;
import com.shop.mappers.ShoppingCartMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    public void get_order(int orderNo, UserDTO userDTO) {
        orderMapper.get_order(OrderDTO.builder()
//                .no(orderNo)
                .user(userDTO)
                .build());
    }

    public List<ShoppingCartDTO> get_order_of_shopping_cart(List<Integer> orderNums, UserDTO userDTO) {
        return shoppingCartMapper.get_shopping_cart_by_user(userDTO, orderNums);
    }

    @Transactional
    public boolean create_order(UserDTO userDTO, Map orderDataMap, List<Integer> cartNumbers) {
        OrderDTO orderDTO = OrderDTO.builder()
                .user(userDTO)
                .shoppingCarts(cartNumbers.parallelStream()
                        .map(number -> ShoppingCartDTO.builder().no(number).build())
                        .toList()
                )
                .createdDate(LocalDateTime.now())
                .addr((String) orderDataMap.get("addr"))
                .impUid((String)orderDataMap.get("imp_uid"))
                .postCode((String)orderDataMap.get("post_code"))
                .amount((Integer) orderDataMap.get("amount"))
                .currency((Integer)orderDataMap.get("currency"))
                .startedAt((Integer)orderDataMap.get("started_at")) // 여기 timestamp로 수정해야함
                .cardQuota((Integer)orderDataMap.get("card_quota"))
                .payMethod((String)orderDataMap.get("pay_method"))
                .pgProvider((String)orderDataMap.get("pg_provider"))
                .cardType((Integer)orderDataMap.get("card_type"))
                .cardName((String)orderDataMap.get("card_name"))
                .cardNumber((String)orderDataMap.get("card_number"))
                .pgId((String)orderDataMap.get("pg_id"))
                .build();

        return true;

    }



}
