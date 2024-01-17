package com.shop.mappers;

import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCartDTO> get_shopping_cart(UserDTO userDTO);
    void insert_shopping_cart(ShoppingCartDTO shoppingCartDTO);
    void insert_shopping_cart_option(ShoppingCartDTO shoppingCartDTO);
}
