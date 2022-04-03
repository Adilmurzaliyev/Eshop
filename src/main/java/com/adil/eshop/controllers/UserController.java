package com.adil.eshop.controllers;

import com.adil.eshop.domain.User;
import com.adil.eshop.repositories.RoleRepository;
import com.adil.eshop.services.UserService;
import com.adil.eshop.services.exceptions.NotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user_views/users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id).orElseThrow(NotFoundExceptions::new));
        model.addAttribute("roles", roleRepository.findAll());
        return "user_views/user_form";
    }

    @GetMapping("/new")
    public String newUser(Model model, User user) {
//        model.addAttribute(new User());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user_views/user_form";
    }

    @PostMapping("/update")
    public String updateUser(User user) {
        userService.createOrUpdate(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String removeUser(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionsHandler(NotFoundExceptions exceptions) {
        ModelAndView modelAndView = new ModelAndView("exception_views/not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
