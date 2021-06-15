package com.tool.test.driver.application.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {
  @NonNull private String username;
  @NonNull private String password;
}
