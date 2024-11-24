package com.java.login.entities;

import jakarta.persistence.*;
import org.hibernate.id.IdentityGenerator;

@Entity
@Table(name = "login_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private int id;
  @Column(name = "username", unique = true)
  private String username;
  @Column(name = "password")
  private String password;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
