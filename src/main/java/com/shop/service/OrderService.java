package com.shop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.OrderMapper;
import com.shop.mappers.ShoppingCartMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;

//    public void get_order(int orderNo, UserDTO userDTO) {
//        orderMapper.get_order(OrderDTO.builder()
////                .no(orderNo)
//                .user(userDTO)
//                .build());
//    }

    public List<ShoppingCartDTO> get_order_of_shopping_cart(List<Integer> orderNums, UserDTO userDTO) {
        return shoppingCartMapper.get_shopping_cart_by_user(userDTO, orderNums);
    }

    @Transactional
    public boolean create_order(UserDTO userDTO, String orderDataString, List<Integer> cartNumbers) throws JsonProcessingException {
//        Long startedAt = Long.valueOf((Integer) orderDataMap.get("started_at"));
//        System.out.println(orderDataMap);

//        System.out.println("orderDataString = " +orderDataString);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 모르는 내용이 있으면 넘어가라
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false); // 알 수 없는 서브타입 오류발생
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true); // 빈 문자열이 오면 null로 처리해줘
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true); // 타임존 셋
        objectMapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true); // Long 타입을 int로 변환
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true); // 이건 뭐지
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE); // 넘겨오는 데이터 case 변환



        OrderDTO orderDTO = objectMapper.readValue(orderDataString, OrderDTO.class);
        orderDTO.setUser(userDTO);
        orderDTO.setShoppingCarts(cartNumbers.parallelStream().map(cartNumber ->
            ShoppingCartDTO.builder().no(cartNumber).build()
        ).toList());


        System.out.println(orderDTO);

        List<ShoppingCartDTO> cartList = shoppingCartMapper.select_cart_list_by_cartNo(cartNumbers);


        int price = orderMapper.get_request_price(cartNumbers);

        if (price != orderDTO.getAmount()) {
            return false;
        }

        // 해당 유저의 ORDER 작성
        orderMapper.create_order(orderDTO);
        // 해당 유저의 ORDER 세부 정보 작성
        orderMapper.create_order_product(orderDTO);



        for (int no : cartNumbers) {
            shoppingCartMapper.delete_cart_by_no(userDTO.getId(), no);
        }

        return true;


//        OrderDTO orderDTO = OrderDTO.builder()
//                .user(userDTO)
//                .shoppingCarts(cartNumbers.parallelStream()
//                        .map(number -> ShoppingCartDTO.builder().no(number).build())
//                        .toList()
//                )
//                .createdDate(LocalDateTime.now())
//                .addr((String) orderDataMap.get("addr"))
//                .impUid((String)orderDataMap.get("imp_uid"))
//                .postCode((String)orderDataMap.get("post_code"))
//                .amount((Integer) orderDataMap.get("amount"))
//                .currency((String)orderDataMap.get("currency"))
//                .startedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(startedAt), ZoneId.systemDefault())) // 여기 timestamp로 수정해야함
//                .cardQuota((Integer)orderDataMap.get("card_quota"))
//                .payMethod((String)orderDataMap.get("pay_method"))
//                .pgProvider((String)orderDataMap.get("pg_provider"))
//                .cardType((Integer)orderDataMap.get("card_type"))
//                .cardName((String)orderDataMap.get("card_name"))
//                .cardNumber((String)orderDataMap.get("card_number"))
//                .pgId((String)orderDataMap.get("pg_id"))
//                .build();

//        return true;

    }


//    // 사용자의 주문 정보 전부 가져오기
//    public List<OrderDTO> get_orders(UserDTO userDTO) {
//
//    }


}
