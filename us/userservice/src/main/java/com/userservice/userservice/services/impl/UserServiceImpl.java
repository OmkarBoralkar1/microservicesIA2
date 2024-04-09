package com.userservice.userservice.services.impl;

import com.userservice.userservice.repositories.UserRepository;
import com.userservice.userservice.entities.User;
import com.userservice.userservice.exceptions.ResourceNotFoundException;
import com.userservice.userservice.services.Userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements Userservice {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        // generate unique user id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        // Implementation for saving a user
        return userRepository.save(user); // Placeholder return statement
    }

    @Override
    public User getUser(String userId) {
        // Implementation for getting a user by ID
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId)); // Placeholder
                                                                                                                  // return
                                                                                                                  // statement
    }

    @Override
    public User UpdateUser(String userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // Update the user's fields with the new values from userDetails
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAbout(userDetails.getAbout());

        // Assuming there are no other fields in the User entity that need to be updated
        // If there are, add similar lines to update those fields

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        userRepository.delete(user);
    }

    @Override
    public List<User> getALLUser() {
        // Implementation for getting all users
        return userRepository.findAll(); // Placeholder return statement
    }
}