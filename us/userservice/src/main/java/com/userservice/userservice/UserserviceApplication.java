package com.userservice.userservice;

import com.userservice.userservice.services.*;



// import com.userservice.userservice.services.externalservice.useerHotelservice;
import com.userservice.userservice.entities.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.*;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// import org.springframework.cloud.netflix.eureka.client.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@Controller
@RequestMapping("/")
@EnableFeignClients
@EnableDiscoveryClient
// @EnableEurekaClient;
// @EnableEurekaClient
public class UserserviceApplication {

    @Autowired
    private Userservice userService;
    // @Autowired
    // private useerHotelservice hotelService;
    @Autowired
    private DiscoveryClient discoveryClient;

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
        return "redirect:/login";
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
    public ResponseEntity<String> updateUser(@PathVariable("userId") String userId, @ModelAttribute User user) {
        System.out.println("updatepost hit");
        try {
            userService.UpdateUser(userId, user); // Attempt to update the user
            String hotelServiceUrl = fetchHotelServiceUrlupdate();

            // Construct the redirect URL with userId and username as query parameters
            String redirectUrl = hotelServiceUrl + "/home?userId=" + userId + "&username=" + user.getUsername();

            // If the update was successful, redirect to the hotel service
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
        } catch (Exception e) {
            // If an exception is thrown, it means the update failed
            return ResponseEntity.ok("Update failed: " + e.getMessage());
        }
    }

    private String fetchHotelServiceUrlupdate() {
        List<ServiceInstance> instances = discoveryClient.getInstances("HOTELSERVICE");
        if (!instances.isEmpty()) {
            ServiceInstance instance = instances.get(0);
            return instance.getUri().toString();
        }
        throw new RuntimeException("HotelserviceApplication not found");
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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // This should match the name of your HTML template file without the .html
                        // extension
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLoginForm(@ModelAttribute User user) {
        User foundUser = userService.findByEmail(user.getEmail());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            // Set userId and username in the session
            // session.setAttribute("userId", foundUser.getUserId());
            // session.setAttribute("username", foundUser.getUsername());

            // // Print session values
            // System.out.println("userId stored in session: " +
            // session.getAttribute("userId"));
            // System.out.println("username stored in session: " +
            // session.getAttribute("username"));

            String hotelServiceUrl = fetchHotelServiceUrl();
            String redirectUrl = hotelServiceUrl + "/home?userId=" + foundUser.getUserId() + "&username="
                    + foundUser.getUsername();
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
        } else {
            return ResponseEntity.ok("Invalid email or password.");
        }
    }

    private String fetchHotelServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("HOTELSERVICE");
        if (!instances.isEmpty()) {
            ServiceInstance instance = instances.get(0);
            return instance.getUri().toString();
        }
        throw new RuntimeException("HotelserviceApplication not found");
    }
}