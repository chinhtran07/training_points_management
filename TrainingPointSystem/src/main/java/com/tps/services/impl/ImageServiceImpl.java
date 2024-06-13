package com.tps.services.impl;

import com.tps.pojo.Image;
import com.tps.repositories.ImageRepository;
import com.tps.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Override
    public void addImage(int postId, String url) {
        Image image = new Image();
        image.setUrl(url);
        image.setPost(postId);
        this.imageRepository.addImage(image);
    }
}
