package com.htn.Shopme.Backend.Service;

import com.htn.Shopme.Backend.Controller.ImageController;
import com.htn.Shopme.Backend.Entity.Role;
import com.htn.Shopme.Backend.Entity.User;
import com.htn.Shopme.Backend.Exception.ResourceNotFoundException;
import com.htn.Shopme.Backend.Repository.RoleRepository;
import com.htn.Shopme.Backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public User createUser(User user, MultipartFile image) throws IOException {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            encodeUserPassword(user);
            user.setRoles(user.getRoles().stream().map(role -> roleRepository.findByName(role.getName())).collect(Collectors.toSet()));
            if (image != null) {
                Integer imageId = imageService.saveImage(image).getId();
                String imageUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ImageController.class).getImage(imageId)).toString();
                user.setPhotos(imageUrl);
            }
            userRepository.save(user);
            return user;
        } else {
            throw new ResourceNotFoundException("This email " + user.getEmail() + " has be used!");
        }
    }

    @Transactional
    public User updateUser(User user) throws ResourceNotFoundException {
        User existUser = getUserByEmail(user.getEmail());
        if (!user.equals(existUser)) {
            Set<Role> updateRole = user.getRoles().stream().map(role -> roleRepository.findByName(role.getName())).collect(Collectors.toSet());
            existUser.setFirstName(user.getFirstName());
            existUser.setLastName(user.getLastName());
            existUser.setPhotos(user.getPhotos());
            existUser.setRoles(updateRole);
            if (!bCryptPasswordEncoder.matches(user.getPassword(), existUser.getPassword())) {
                existUser.setPassword(user.getPassword());
                encodeUserPassword(existUser);
            }
            userRepository.save(existUser);
        }
        return existUser;
    }

    @Transactional
    public void deleteUser(Integer userId) throws ResourceNotFoundException {
            User existUser = getUserById(userId);
            userRepository.delete(existUser);
    }

    private void encodeUserPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    private User getUserById(Integer id) throws ResourceNotFoundException {
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find any user with id: " + id));
    }

    private User getUserByEmail(String email) throws ResourceNotFoundException {
            return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Could not find any user with email: "+ email));
    }
}
