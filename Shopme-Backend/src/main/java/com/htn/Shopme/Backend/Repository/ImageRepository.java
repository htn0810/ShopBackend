package com.htn.Shopme.Backend.Repository;

import com.htn.Shopme.Backend.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findById(Integer imageId);

}
