package com.htn.Shopme.Backend.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htn.Shopme.Backend.Controller.ImageController;
import com.htn.Shopme.Backend.Entity.Brand;
import com.htn.Shopme.Backend.Repository.BrandRepository;
import com.htn.Shopme.Backend.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final ImageService imageService;

    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    public Brand createNewBrand(Brand newBrand, MultipartFile imageFile) throws IOException {
        if(brandRepository.findByName(newBrand.getName()).isEmpty() && imageFile != null) {
            Integer imageId = imageService.saveImage(imageFile).getId();
            String imageUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ImageController.class).getImage(imageId)).toString();
            newBrand.setImage(imageUrl);
            return brandRepository.save(newBrand);
        }
        return null;
    }

    public Brand updateBrand(Brand updateBrand, MultipartFile imageFile) throws IOException {
        if(brandRepository.findByName(updateBrand.getName()).isEmpty() && imageFile != null) {
            Integer imageId = imageService.saveImage(imageFile).getId();
            String imageUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ImageController.class).getImage(imageId)).toString();
            Brand existedBrand = brandRepository.findById(updateBrand.getId()).get();
            if (brandRepository.findById(updateBrand.getId()).isPresent() && existedBrand.getImage() != null) {
                int theLastSeperator = existedBrand.getImage().lastIndexOf("/");
                if (theLastSeperator != -1) {
                    Integer existImageId = Integer.parseInt(existedBrand.getImage().substring(theLastSeperator + 1));
                    imageService.deleteImage(existImageId);
                }
            }
            existedBrand = new ObjectMapper().updateValue(existedBrand, updateBrand);
            existedBrand.setImage(imageUrl);
            return brandRepository.save(existedBrand);
        }
        return null;
    }

    public boolean deleteBrand(Integer id) {
        if (brandRepository.findById(id).isPresent()) {
            Brand existedBrand = brandRepository.findById(id).get();
            if (existedBrand.getImage() != null) {
                int theLastSeperator = existedBrand.getImage().lastIndexOf("/");
                if (theLastSeperator != -1) {
                    Integer imageId = Integer.parseInt(existedBrand.getImage().substring(theLastSeperator + 1));
                    imageService.deleteImage(imageId);
                }
            }
            brandRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
