package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUser(Model model, Principal principal) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", userService.findByName(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }
    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        User user = new User();
        model.addAttribute("nuser",user);
        model.addAttribute("roles", roleService.findAll());
        return "new";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {

        userService.update(user, roleService.findAll());
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value= "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }



}
