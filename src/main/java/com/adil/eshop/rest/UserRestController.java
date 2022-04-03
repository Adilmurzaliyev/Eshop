package com.adil.eshop.rest;

import com.adil.eshop.domain.Product;
import com.adil.eshop.domain.User;
import com.adil.eshop.dto.ProductDTO;
import com.adil.eshop.dto.UserDTO;
import com.adil.eshop.services.UserService;
import com.adil.eshop.services.exceptions.NotFoundExceptions;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Api", description = "User Api")
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public UserDTO findById(@PathVariable(value = "id") Long id) {
        User user = userService.findById(id).orElseThrow(NotFoundExceptions::new);
        return toDTO(user);
    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        return userService.createOrUpdate(user);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user) {
        if (userService.existById(user.getId())){
           userService.createOrUpdate(user);
           return user;
        }
        throw  new NotFoundExceptions();
    }

    @DeleteMapping("/{id}/id")
    public void deleteById(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundExceptions e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .productDTOS(toDTOs(user.getProducts()))
                .build();
    }

    private List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(p -> toDTO(p)).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
