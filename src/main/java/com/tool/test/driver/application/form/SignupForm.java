package com.tool.test.driver.application.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {
  private String email;
  private String password;
  private String name;
}
