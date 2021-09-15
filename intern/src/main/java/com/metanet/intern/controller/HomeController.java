package com.metanet.intern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@GetMapping(value = {"/","/index"})
	public String index() {
		return "thymeleaf/index";
	}
	
	@RequestMapping("/hello")
	public String test2() {
		return "thymeleaf/hello";
	}
	@RequestMapping("/default")
	public String test3() {
		return "thymeleaf/default";
	}
	
	@GetMapping("/login")
	public String longinView() {
		return "thymeleaf/login";
	}
//	@RequestMapping("/topbar")
//	public String frag_top() {
//		return "thymeleaf/topbar";
//	}
//	@RequestMapping("/footer")
//	public String frag_foot() {
//		return "thymeleaf/footer";
//	}
//	@RequestMapping("/head")
//	public String frag_head() {
//		return "thymeleaf/head";
//	}
//	@RequestMapping("/script")
//	public String frag_script() {
//		return "thymeleaf/script";
//	}
//	@RequestMapping("/sidebar")
//	public String frag_side() {
//		return "thymeleaf/sidebar";
//	}
}
