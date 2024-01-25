package com.shop.mappers;

import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {



    void create_order(UserDTO userDTO);

    void create_order_cart(OrderDTO orderDTO);

    void get_order(OrderDTO orderDTO);
}
