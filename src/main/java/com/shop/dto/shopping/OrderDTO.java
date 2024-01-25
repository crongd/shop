package com.shop.dto.shopping;

import com.shop.dto.user.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int no;
    private UserDTO user;
    private List<ShoppingCartDTO> shoppingCarts;
    private LocalDateTime createdDate;


}
