package com.shop.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private int no;
    private String name;
    private int parentNo;
    private int level;
}
