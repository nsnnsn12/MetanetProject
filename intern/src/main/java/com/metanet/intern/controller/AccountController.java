package com.metanet.intern.controller;

import org.springframework.core.io.Resource;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.enummer.Role;
import com.metanet.intern.service.ManagerService;
import com.metanet.intern.service.StorageService;
import com.metanet.intern.vo.ManagerSearchCondition;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/account")
public class AccountController {
	@Autowired
	ManagerService managerService;

	@Autowired
	StorageService storageService;
	
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
	public String accountList(@ModelAttribute("condition") ManagerSearchCondition condition, Pageable pageable, Model model) {
		log.info(condition.toString());
		Page<Manager> page = managerService.searchManagerList(pageable, condition);
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		return "thymeleaf/account/account_list";
	}
	
	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") ManagerSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Page<Manager> page = managerService.searchManagerList(PageRequest.of(pageNo, 10), condition);
		model.addAttribute("page", page);
		Pager pager = new Pager(page.getSize(), 5, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
		return "thymeleaf/account/account_list";
	}
	
	@GetMapping("mangerDetail/{id}")
	public String mangerDetail(@PathVariable("id")Manager manager, Model model) {
		//사진을 등록하지 않았을 경우
		preventPhotoNull(manager);
		model.addAttribute("detailObject", manager);
		return"thymeleaf/account/account_detail";
	}

	public void preventPhotoNull(Manager manager) {
		if(manager.getPhoto() == null) {
			manager.setPhoto(new PhotoFile());
			manager.getPhoto().setId((long)0);
		}
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
	
	@GetMapping("download/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id")PhotoFile photoFile) {
		Resource file = storageService.loadAsResource(photoFile.getSaveFileName());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
}
