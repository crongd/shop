package com.shop.service;

import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.OrderMapper;
import com.shop.mappers.ShoppingCartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    public void get_order(int orderNo, UserDTO userDTO) {
        orderMapper.get_order(OrderDTO.builder()
                .no(orderNo)
                .user(userDTO)
                .build());
    }

    public List<ShoppingCartDTO> get_order_of_shopping_cart(List<Integer> orderNums, UserDTO userDTO) {
        return shoppingCartMapper.get_shopping_cart_by_user(userDTO, orderNums);
    }

    @Transactional
    public void create_order(List<Integer> orderNum, UserDTO userDTO) {
        orderMapper.create_order(userDTO);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(userDTO);
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();

        orderDTO.setShoppingCarts(new ArrayList<>());
        orderNum.forEach(number -> {
            orderDTO.getShoppingCarts().add(ShoppingCartDTO.builder().no(number).build());
//            ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//            shoppingCartDTO.setNo(number);

//            shoppingCartDTOS.add(shoppingCartDTO);
        });
//        orderDTO.setShoppingCarts(shoppingCartDTOS);

//        System.out.println("insert_user_order 들어옴");

        orderMapper.create_order_cart(orderDTO);
    }



}
