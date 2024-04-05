package com.shop.dto.product;

import com.shop.dto.user.UserDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

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
    private String formatWriteDate;
    private List<String> users;
    private double averageRate;
}
