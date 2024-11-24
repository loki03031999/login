package com.java.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

  @GetMapping(path = "public/accessdenied")
  public String accessDeniedPage() {
    return "accessDeniedPage";
  }

}
