package com.metanet.intern.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.service.ManagerService;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/account")
public class AccountController {
	@Autowired
	ManagerService managerService;

	@GetMapping("join")
	public String joinForm(Manager manager) {
		return "thymeleaf/account/register";
	}

	@PostMapping("join")
	public String regist(Manager manager) {
		managerService.join(manager);
		log.info(manager.toString());
		return "redirect:joinSuccess";
	}

	@GetMapping("joinSuccess")
	public String joinSuccess() {
		return "thymeleaf/account/join_success";
	}
	
	@GetMapping("joinFail")
	public String joinFail(Model model) {
		return "thymeleaf/account/join_fail";
	}
	
	@RequestMapping("loginFail")
	public String loginFail(Model model) {
		return "thymeleaf/account/login_fail";
	}

	
	@GetMapping("list")
	public String accountList(Pageable pageable, Model model) {
		Page<Manager> page = managerService.findAllManagers(pageable);
		model.addAttribute("managerList", page.getContent());
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		return "thymeleaf/account/account_list";
	}
	
	@GetMapping("mangerDetail/{id}")
	public String mangerDetail(@PathVariable("id")Manager manager, Model model) {
		model.addAttribute("detailObject", manager);
		return"thymeleaf/account/account_detail";
	}

	@ExceptionHandler(IllegalStateException.class)
	public String illegalEx(IllegalStateException e, RedirectAttributes attributes) {
		log.info(e.getMessage());
		attributes.addFlashAttribute("errMessage", e.getMessage());
		return "redirect:joinFail";
	}
	
	@GetMapping("accept")
	public String updateAccept(Long id, Integer acceptFlag) {
		managerService.updateAccept(id, acceptFlag);
		return "redirect:list";
	}
	
	@GetMapping("delete")
	public String deleteManager(Long id) {
		Integer deleteFlag = 1;
		managerService.delete(id, deleteFlag);
		return "redirect:list";
	}
}
