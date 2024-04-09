package com.userservice.userservice.services;

import com.userservice.userservice.entities.User; // Make sure to import the User class
import java.util.*;

public interface Userservice {

    // User operations

    // Create
    User saveUser( User user);

    // Get all users
    List<User> getALLUser();

    // Get single user of given user id
    User getUser(String userId);

    // Delete
    void deleteUser(String userId);

    // Update
    User UpdateUser(String userId, User user);
}