package com.htn.Shopme.Backend.Repository;

import com.htn.Shopme.Backend.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String categoryName);

    Category findByNameOrAlias(String name, String alias);

}
