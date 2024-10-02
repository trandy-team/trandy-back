package org.trandy.trandy_server.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trandy.trandy_server.image.domain.Image;
import org.trandy.trandy_server.image.repository.ImageRepository;
import org.trandy.trandy_server.post.domain.Post;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image saveImage(String imageUrl, Post post){

        return imageRepository.save(Image.builder()
                .imageUrl(imageUrl)
                .post(post)
                .build());
    }
}
