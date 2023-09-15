package com.htn.Shopme.Backend.Controller;

import com.htn.Shopme.Backend.Entity.Brand;
import com.htn.Shopme.Backend.Service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAll() {
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @PostMapping("/create")
    public ResponseEntity<Brand> createNewBrand(@RequestPart("brand") Brand brand, @RequestPart("image")MultipartFile imageFile) throws URISyntaxException, IOException {
        Brand newBrand =  brandService.createNewBrand(brand, imageFile);
        if (newBrand != null) {
            return ResponseEntity.created(new URI("/api/brand/" + brand.getName())).body(brand);
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Name of brand is existed!");
    }

    @PutMapping("/update")
    public ResponseEntity<Brand> updateBrand(@RequestPart("brand") Brand brand, @RequestPart("image")MultipartFile imageFile) throws IOException {
        Brand updateBrand = brandService.updateBrand(brand, imageFile);
        if (updateBrand != null) {
            return ResponseEntity.ok(brand);
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Name of brand is existed!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("id") Integer id) {
        if (brandService.deleteBrand(id)) {
            return ResponseEntity.noContent().build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find Brand with id: " + id);
    }
}
