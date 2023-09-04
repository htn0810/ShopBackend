package com.htn.Shopme.Backend.DTO;

import com.htn.Shopme.Backend.Entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
public class CategoryDTO {
    private String name;
    private String alias;
    private String image;
    private boolean enabled = true;
    private Category parent;
}
