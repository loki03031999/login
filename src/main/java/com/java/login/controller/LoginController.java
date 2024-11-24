package com.java.login.controller;

import com.java.login.entities.User;
import com.java.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

  Logger logger = LoggerFactory.getLogger(LoginController.class);
  @Autowired
  UserService userService;

  @GetMapping(path = "/public/login")
  public String getLoginPage(@RequestParam(value = "state", required = false) String state,
                             Model model,
                             HttpServletRequest httpServletRequest) {

    populateModelWithSessionDetails(httpServletRequest, model);
    logger.info("GET /public/login page accessed, with param state - {}, {}", state, model.getAttribute("state"));
    if (state != null) {
      String stateMessage = getStateMessage(state);
      model.addAttribute("stateMessage", stateMessage);
    }

    model.addAttribute("user", new User());

    return "login";
  }

  @PostMapping(path = "/public/login")
  public String afterLoginPage(@ModelAttribute("user") User user,
                               RedirectAttributes attributes,
                               HttpServletRequest httpServletRequest,
                               BindingResult bindingResult) {
    User userFromDb = userService.getUser(user.getUsername());
    if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
      httpServletRequest.getMethod();
      httpServletRequest.getSession().invalidate();

      HttpSession httpSession = httpServletRequest.getSession();


      httpServletRequest.getSession().setAttribute("user", user.getUsername());
      httpServletRequest.getSession().setAttribute("isAuthenticated", true);
      return "redirect:/home";
    }
    attributes.addAttribute("state", "error");
    return "redirect:/public/login";
  }

  @GetMapping(path = "/home")
  public String homePage(Model model, HttpServletRequest httpServletRequest) {
    populateModelWithSessionDetails(httpServletRequest, model);
    logger.info("User - {}", httpServletRequest.getSession().getAttribute("user"));
    model.addAttribute("account", httpServletRequest.getSession().getAttribute("user"));

    return "home";
  }

  private void populateModelWithSessionDetails(HttpServletRequest httpServletRequest, Model model) {
    model.addAttribute("sessionId", httpServletRequest.getSession().getId());
  }

  private String getStateMessage(String state) {
    if ("unauthenticated".equals(state)) return "Unauthenticated, Please login first";
    if ("error".equals(state)) return "Error, Please login again";
    return "";
  }

}
