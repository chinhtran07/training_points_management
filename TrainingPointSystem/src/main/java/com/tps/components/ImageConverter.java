package com.tps.components;

import com.tps.dto.ImageDTO;
import com.tps.pojo.Image;
import com.tps.pojo.MissingReportImage;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public ImageDTO toDTO(Image image) {
        ImageDTO imagesDTO = new ImageDTO();

        imagesDTO.setUrl(image.getUrl());
        imagesDTO.setId(image.getId());

        return imagesDTO;
    }

    public ImageDTO toDTO(MissingReportImage image) {
        ImageDTO imagesDTO = new ImageDTO();

        imagesDTO.setUrl(image.getUrl());
        imagesDTO.setId(image.getId());

        return imagesDTO;
    }

    public Image toEntity(ImageDTO imageDTO) {
        Image image = new Image();

        image.setUrl(imageDTO.getUrl());
        image.setId(imageDTO.getId());

        return image;
    }


}
