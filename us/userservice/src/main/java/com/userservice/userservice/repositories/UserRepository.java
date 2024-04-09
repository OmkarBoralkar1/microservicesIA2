package com.userservice.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.userservice.userservice.entities.User; // Make sure to import the User class

public interface UserRepository extends JpaRepository<User, String> {
//In this we implement any custom or query
//write


}