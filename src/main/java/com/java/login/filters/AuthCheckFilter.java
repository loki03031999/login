package com.java.login.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Order(1)
@Component
public class AuthCheckFilter extends HttpFilter {

  private static final String IS_AUTHENTICATED = "isAuthenticated";

  private static final String[] URI_NOT_HANDLED_BY_AUTH_FILTER = new String[]{
          "/public",
          "/favicon",
          "/css",
          "/js"
  };

  Logger logger = LoggerFactory.getLogger(AuthCheckFilter.class);

  private boolean hasActiveSession(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getSession(false) != null && httpServletRequest.getSession().getAttribute(IS_AUTHENTICATED) != null && (boolean) httpServletRequest.getSession().getAttribute(IS_AUTHENTICATED);
  }

  protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    logger.info("Auth Filter Invoked");

    logger.info("Request received from URI - {}", request.getRequestURI());

    if (!isRequestProcessedByAuthFilter(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!hasActiveSession(request)) {
      response.sendRedirect("/public/login?state=unauthenticated");
      return;
    }

    filterChain.doFilter(request, response);
    logger.info("Auth Filter Completed");
  }

  /**
   * returns true if request to be handled by auth-filter
   *
   * @param request
   * @return
   */
  private boolean isRequestProcessedByAuthFilter(HttpServletRequest request) {
    for (String uriPrefix : URI_NOT_HANDLED_BY_AUTH_FILTER) {
      if (StringUtils.startsWith(request.getRequestURI(), uriPrefix)) {
        return false;
      }
    }
    return true;
  }

}
