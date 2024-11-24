package com.java.login.controller;

import com.java.login.entities.User;
import com.java.login.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountCreationController {

  @Autowired
  UserService userService;

  Logger logger = LoggerFactory.getLogger(AccountCreationController.class);

  @GetMapping(path = "/public/createaccount")
  public String getAccountCreationPage(@RequestParam(value = "state", required = false) String state, Model model) {
    if (!StringUtils.isEmpty(state) && "error".equals(state))
      model.addAttribute("error", "Error occurred during account creation");
    return "login";
  }

  @PostMapping(path = "/public/createaccount")
  public String createAccount(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
    try {
      user = userService.createUser(user.getUsername(), user.getPassword());
      model.addAttribute("username", user.getUsername());
    }
    catch (Exception exception) {
      logger.error("Exception - {}", exception.getMessage());
      model.addAttribute("errorMessage", exception.getMessage());
      return "login";
    }
    return "redirect:/public/login";

  }

}
