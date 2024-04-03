package com.shop.dto.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private int no;
    private String userId;
    private int productNo;
    private String content;
    private int rate;
    private LocalDateTime writeDate;
    private double averageRate;
}
