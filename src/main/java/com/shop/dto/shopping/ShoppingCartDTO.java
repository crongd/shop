package com.shop.dto.shopping;


import com.shop.dto.product.ProductDTO;
import com.shop.dto.user.UserDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {
    private int no;
    private UserDTO user;
    private ProductDTO product;
    private int amount;
}
