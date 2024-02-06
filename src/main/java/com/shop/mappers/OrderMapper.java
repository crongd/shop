package com.shop.mappers;

import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {



    void create_order(OrderDTO orderDTO);

    void create_order_product(OrderDTO orderDTO);

//    void create_order_option(OrderDTO orderDTO);


    List<OrderDTO> get_orders(UserDTO userDTO);

    int get_request_price(List<Integer> cartNO);

    @MapKey("id")
    @Select("select id, password from user")
    List<Map<String, String>> test();


}
