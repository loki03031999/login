package com.java.login.service;

import com.java.login.entities.User;
import com.java.login.repository.UserRepository;
import jakarta.persistence.UniqueConstraint;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User getUser(String userName) {
    return userRepository.findByUsername(userName).orElse(null);
  }

  public User createUser(String userName, String password) {
    validateUserName(userName);
    validatePassword(password);

    User user = new User(userName, password);
    return userRepository.save(user);
  }

  private void validateUserName(String userName) {
    userName = StringUtils.deleteWhitespace(userName);

    if (StringUtils.isEmpty(userName)) {
      throw new IllegalArgumentException("username cannot be empty");
    }
  }

  private void validatePassword(String password) {
    password = StringUtils.deleteWhitespace(password);

    if (StringUtils.isEmpty(password)) {
      throw new IllegalArgumentException("password cannot be empty");
    }
  }


}
