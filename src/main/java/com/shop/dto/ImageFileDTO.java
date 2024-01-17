package com.shop.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@Builder
@ToString(exclude = "data")
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileDTO {
    private int no;
    private byte[] data;
    private MultipartFile file;
    private String originalFileName;
    private String savedFileName;
}
