package com.htn.Shopme.Backend.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htn.Shopme.Backend.Controller.ImageController;
import com.htn.Shopme.Backend.DTO.CategoryDTO;
import com.htn.Shopme.Backend.Entity.Category;
import com.htn.Shopme.Backend.Exception.ResourceNotFoundException;
import com.htn.Shopme.Backend.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category addNewCategory(CategoryDTO categoryDTO, MultipartFile imageFile) throws IOException {
        Category newCategory = new Category();
        newCategory = new ObjectMapper().updateValue(newCategory, categoryDTO);
        if (imageFile != null) {
            Integer imageId = imageService.saveImage(imageFile).getId();
            String imageUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ImageController.class).getImage(imageId)).toString();
            newCategory.setImage(imageUrl);
        }
        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(String categoryName, CategoryDTO categoryDTO, MultipartFile imageFile) throws IOException {
        Category existCategory = categoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Cannot find category : " + categoryName));
        existCategory = new ObjectMapper().updateValue(existCategory, categoryDTO);
        if (imageFile != null) {
            if (existCategory.getImage() != null) {
                int theLastSeperator = existCategory.getImage().lastIndexOf("/");
                if (theLastSeperator != -1) {
                    Integer existImageId = Integer.parseInt(existCategory.getImage().substring(theLastSeperator + 1));
                    imageService.deleteImage(existImageId);
                }
            }
            Integer imageId = imageService.saveImage(imageFile).getId();
            String imageUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ImageController.class).getImage(imageId)).toString();
            existCategory.setImage(imageUrl);
        }
        return categoryRepository.save(existCategory);
    }
}
