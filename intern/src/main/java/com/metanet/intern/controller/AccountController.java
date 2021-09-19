package com.metanet.intern.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.metanet.intern.domain.Manager;

@Controller
public class AccountController {
	@PostMapping
	public String join(@Valid Manager manager) {
		return "";
	}
}
