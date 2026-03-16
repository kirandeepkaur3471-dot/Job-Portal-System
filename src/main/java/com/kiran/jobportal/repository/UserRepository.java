package com.kiran.jobportal.repository;

import com.kiran.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    User findByEmail(String email);

}