package com.htn.Shopme.Backend.DTO;

import com.htn.Shopme.Backend.Entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class InfoCategoryDTO {
    private String name;
    private String alias;
    private String image;
    private boolean enabled;
    private Category parent;
    private Set<Category> children;

    public InfoCategoryDTO(final Category category) {
        this.name = category.getName();
        this.alias = category.getAlias();
        this.image = category.getImage();
        this.parent = category.getParent();
        this.enabled = category.isEnabled();
        this.children = category.getChildren();
    }
}
