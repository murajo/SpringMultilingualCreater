package com.example.springmc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringmcController {

	@Autowired
	private SpringmcService service;

	@GetMapping("/")
	public String getRoot() {
		return (service.rootRedirectJudge() ? "redirect:translate" : "redirect:register");
	}

	@GetMapping("/register")
	public String getRegister() {
		return service.registerRedirectJudge() ? "redirect:translate" : "springmc/register";
	}

	@GetMapping("/translate")
	public String getTranslate() {
		return service.translateRedirectJudge() ? "springmc/translate" : "redirect:register";
	}

}
