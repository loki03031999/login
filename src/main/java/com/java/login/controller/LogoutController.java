package com.java.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogoutController {

  @GetMapping(path = "/public/logout")
  public String logoutUser(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
    HttpSession session = httpServletRequest.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    redirectAttributes.addAttribute("state", "logout");
    return "redirect:/public/login";
  }

}
