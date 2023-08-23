package com.htn.Shopme.Backend.Controller;

import com.htn.Shopme.Backend.DTO.UserDTO;
import com.htn.Shopme.Backend.Entity.User;
import com.htn.Shopme.Backend.Exception.ResourceNotFoundException;
import com.htn.Shopme.Backend.Service.ImageService;
import com.htn.Shopme.Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        try {
            return ResponseEntity.ok(userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList()));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HTTP Status will be NOT FOUND (CODE 404)\n");
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestPart("user") User user, @RequestPart("image") MultipartFile file) throws URISyntaxException, ResourceNotFoundException, IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(user.toString());
        User newUser = userService.createUser(user, file);
        return ResponseEntity.created(new URI("/api/user/" + user.getId())).body(new UserDTO(newUser));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) throws ResourceNotFoundException {
        return ResponseEntity.ok(new UserDTO(userService.updateUser(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
