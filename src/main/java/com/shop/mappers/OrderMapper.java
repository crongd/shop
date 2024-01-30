package com.shop.mappers;

import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {



    void create_order(OrderDTO orderDTO);

    void create_order_cart(OrderDTO orderDTO);

    void get_order(OrderDTO orderDTO);

    int get_request_price(List<Integer> cartNO);
}
