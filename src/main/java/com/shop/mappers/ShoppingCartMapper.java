package com.shop.mappers;

import com.shop.dto.product.ProductDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCartDTO> get_shopping_cart(UserDTO userDTO);
    void insert_shopping_cart(ShoppingCartDTO shoppingCartDTO);
    void insert_shopping_cart_option(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCartDTO> get_shopping_cart_by_user(@Param("user") UserDTO userDTO,@Param("orderNums") List<Integer> orderNums);

    void change_product_amount(ShoppingCartDTO shoppingCartDTO);

    void delete_product(ShoppingCartDTO shoppingCartDTO);
}
