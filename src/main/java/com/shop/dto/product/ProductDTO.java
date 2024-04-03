package com.shop.dto.product;

import com.shop.dto.ImageFileDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private int no;
    private String title;

    private int price;
    private int deliveryPrice;
    private String mainImg;
    private LocalDateTime writeDate;
    private Category category;

    private List<ImageFileDTO> productImages;
    private List<ProductOptionDTO> productOptions;
    private List<ReviewDTO> reviews;

}
