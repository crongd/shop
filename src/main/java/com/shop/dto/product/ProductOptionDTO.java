package com.shop.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionDTO {
    private int no;
    private int productNo;
    private String name;
    private int additionalPrice;
}
