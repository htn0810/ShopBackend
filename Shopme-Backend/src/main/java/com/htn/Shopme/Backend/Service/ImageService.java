package com.htn.Shopme.Backend.Service;

import com.htn.Shopme.Backend.Entity.Image;
import com.htn.Shopme.Backend.Exception.ResourceNotFoundException;
import com.htn.Shopme.Backend.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;

    public Image getImage(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can not found image with id: " + id));
    }

    public Image saveImage (MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Image image = new Image(fileName, file.getContentType(), file.getBytes());
        return imageRepository.save(image);
    }

    public void deleteImage(Integer id) {
        if (imageRepository.findById(id).isPresent()) {
            imageRepository.deleteById(id);
        }
    }


}
