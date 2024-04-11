package com.userservice.userservice;

import com.userservice.userservice.services.*;
import com.userservice.userservice.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.netflix.eureka.client.EnableEurekaClient;


@SpringBootApplication
@Controller
@RequestMapping("/")
// @EnableEurekaClient;
// @EnableEurekaClient
public class UserserviceApplication {

    @Autowired
    private Userservice userService;

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    // Existing methods...

    // Method to redirect to the user list page
    @GetMapping("/list")
    public String getAllUsers(Model model) {
        List<User> users = userService.getALLUser();
        model.addAttribute("users", users);
        return "list"; // This should match the name of your HTML template file without the .html
                       // extension
    }

    // Method to show the create user form
    // Method to show the create user form
    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        System.out.println("createrouter hit");
        model.addAttribute("user", new User());
        return "create";
    }

    @GetMapping("/test")
    public String test() {
        return "Hello, world!";
    }

    // Method to handle the creation of a new user
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/list";
    }

    // Method to show the update user form
    @GetMapping("/update/{userId}")
    public String showUpdateUserForm(@PathVariable("userId") String id, Model model) {
        System.out.println("updateget hit");
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "update";
    }

    // Method to handle the update of a user
    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @ModelAttribute User user) {
        System.out.println("updatepost  hit");
        userService.UpdateUser(userId, user); // Assuming saveUser handles updates as well
        return "redirect:/list";
    }

    // Method to delete a user
    @GetMapping("/delete/{userId}")
    public String Showdelete(@PathVariable("userId") String userId) {
        System.out.println("deletepost  hit");
        userService.deleteUser(userId);
        return "delete";
    }
    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        System.out.println("deletepost  hit");
        userService.deleteUser(userId);
        return "redirect:/list";
    }
}