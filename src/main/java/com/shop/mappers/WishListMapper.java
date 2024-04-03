package com.shop.mappers;

import com.shop.dto.product.ProductDTO;
import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishListMapper {
    List<ProductDTO> wish_list_get(UserDTO userDTO);

    void wish_list_add(@Param("user") UserDTO userDTO, @Param("productNo") int productNo);

    void wish_list_delete(@Param("user") UserDTO userDTO, @Param("productNo") int productNo);
}
