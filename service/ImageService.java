package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    //저장
    @Transactional
    public void save(Image image) {
        imageRepository.save(image);
    }

    //삭제
    @Transactional
    public void delete(Image image) {
        imageRepository.delete(image);
    }

    //조회
    public Image findOne(Long id) {
        Image image = imageRepository.findById(id).get();
        return image;
    }

    //모든 조회
    public List<Image> findAll() {
        List<Image> images = imageRepository.findAll();
        return images;
    }
}
