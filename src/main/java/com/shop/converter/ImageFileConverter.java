package com.shop.converter;


import com.shop.dto.ImageFileDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;


@Log4j2
public class ImageFileConverter implements Converter<MultipartFile, ImageFileDTO> {

    @Override
    public ImageFileDTO convert(MultipartFile source) {
        if (source.isEmpty()) {
            return null;
        }

        try {
            String UUID = java.util.UUID.randomUUID().toString();
            ImageFileDTO imageFileDTO = new ImageFileDTO();
            imageFileDTO.setData(source.getBytes());
            imageFileDTO.setFile(source);
            imageFileDTO.setSavedFileName(UUID + "-" + source.getOriginalFilename());
            imageFileDTO.setOriginalFileName(source.getOriginalFilename());

            return imageFileDTO;

        } catch (Exception e) {
            log.error("ImageFileConverter error: " + e.getMessage());
            return null;
        }
    }
}
