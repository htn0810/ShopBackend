package com.htn.Shopme.Backend.DTO;

import com.htn.Shopme.Backend.Entity.Role;
import com.htn.Shopme.Backend.Entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String photos;
    private Set<Role> roles;

    public UserDTO(final User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.photos = user.getPhotos();
        this.roles = user.getRoles();
    }
}
