package com.tool.test.driver.application.controller;

import com.tool.test.driver.application.form.SignupForm;
import com.tool.test.driver.domain.exception.UserDuplicateException;
import com.tool.test.driver.domain.service.SignupService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignupController {

  private final SignupService signupService;

  @Autowired
  public SignupController(SignupService signupService) {
    this.signupService = signupService;
  }

  @PostMapping(value = "signup")
  // Formデータでクレデンシャルをもらい、認証を行う
  public void signup(@Validated @RequestBody SignupForm form, HttpServletResponse response) {
    List<String> roles = new ArrayList<String>();
    roles.add("ROLE_USER");

    try {
      signupService.register(form.getUsername(), form.getPassword(), roles);
      // HTTP Status 200
      response.setStatus(HttpStatus.OK.value());
    } catch (UserDuplicateException e) {
      // HTTP Status 200
      response.setStatus(HttpStatus.CONFLICT.value());
    }
  }
}
