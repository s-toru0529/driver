package com.tool.test.driver.application.controller;

import com.tool.test.driver.application.form.SignupForm;
import com.tool.test.driver.domain.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {
  private final AccountService accountService;

  public IndexController(AccountService accountService) {
    this.accountService = accountService;
  }

  /**
   * トップページ
   *
   * @param signupForm サインアップフォームデータ
   * @param model モデル（ユーザーリスト）
   * @return index
   */
  @GetMapping(value = "/")
  public String index() {
    return "forward:vue/index.html";
  }

  /**
   * アカウント登録処理
   *
   * @param signupForm サインアップフォームデータ
   * @param redirectAttributes リダイレクト先へメッセージを送るため
   * @return index (redirect)
   */
  @PostMapping(value = "signup")
  public String signup(
      @ModelAttribute("signup") SignupForm signupForm, RedirectAttributes redirectAttributes) {
    // TODO 暫定的に2つのロールを付与する
    String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
    accountService.register(
        signupForm.getName(), signupForm.getEmail(), signupForm.getPassword(), roles);
    redirectAttributes.addFlashAttribute("successMessage", "アカウントの登録が完了しました");
    return "redirect:/";
  }
}
