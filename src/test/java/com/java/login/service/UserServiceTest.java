package com.java.login.service;

import com.java.login.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  void getUserUserExist() {
    userService.createUser("user1", "user1");
    User testUser = userService.getUser("user1");

    assertNotNull(testUser);
    assertEquals("user1", testUser.getUsername());
  }

  @Test
  void getUserUserDoesNotExist() {
    userService.getUser("user1");
  }

  @Test
  void createUserUserNameUniqueTest() {
    userService.createUser("user1", "user1");
    assertThrows(DataIntegrityViolationException.class, () -> userService.createUser("user1", "user1"));
  }
}