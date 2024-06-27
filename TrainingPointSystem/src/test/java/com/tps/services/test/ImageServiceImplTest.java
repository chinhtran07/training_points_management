package com.tps.services.test;

import com.tps.pojo.Image;
import com.tps.repositories.ImageRepository;
import com.tps.services.impl.ImageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void addImage() {
        int postId = 1;
        String url = "https://example.com/image.jpg";

        Image expectedImage = new Image();
        expectedImage.setPost(postId);
        expectedImage.setUrl(url);

        doNothing().when(imageRepository).addImage(expectedImage);

        imageService.addImage(postId, url);

        verify(imageRepository).addImage(expectedImage);
    }
}