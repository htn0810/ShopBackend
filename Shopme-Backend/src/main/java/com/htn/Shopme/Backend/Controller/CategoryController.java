package com.htn.Shopme.Backend.Controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.htn.Shopme.Backend.DTO.CategoryDTO;
import com.htn.Shopme.Backend.DTO.InfoCategoryDTO;
import com.htn.Shopme.Backend.Entity.Category;
import com.htn.Shopme.Backend.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<InfoCategoryDTO>> getAllCategory() {
        return ResponseEntity.ok(categoryService.listAll().stream().map(InfoCategoryDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestPart("category") CategoryDTO categoryDTO, @RequestPart("image")MultipartFile imageFile) throws IOException, URISyntaxException {
        Category newCategory = categoryService.addNewCategory(categoryDTO, imageFile);
        return ResponseEntity.created(new URI("/api/category/" + newCategory.getName())).body(newCategory);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<InfoCategoryDTO> updateCategory(@PathVariable String name,
                                                   @RequestPart("category") CategoryDTO categoryDTO,
                                                   @RequestPart("image") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok( new InfoCategoryDTO(categoryService.updateCategory(name, categoryDTO, imageFile)));
    }
}
