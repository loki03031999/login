package com.java.login.repository;

import com.java.login.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

  public Optional<User> findByUsername(String username);

}
