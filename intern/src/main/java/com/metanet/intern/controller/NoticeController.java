package com.metanet.intern.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Notice;
import com.metanet.intern.repository.NoticeRepository;
import com.metanet.intern.service.NoticeService;
import com.metanet.intern.vo.EducationSearchCondition;
import com.metanet.intern.vo.ManagerSearchCondition;
import com.metanet.intern.vo.Pager;
import com.metanet.intern.vo.SearchCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	
	@GetMapping("list")
	public String list(@ModelAttribute("condition") SearchCondition condition,@PageableDefault(sort = {"createDate"}, direction = Direction.DESC) Pageable pageable, Model model) {
		log.info(condition.toString());
		paging(condition, model, pageable);
		return "thymeleaf/notice/notice_list";
	}
	
	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") ManagerSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Pageable pageable = PageRequest.of(pageNo, 10);
		paging(condition, model, pageable);
		return "thymeleaf/notice/notice_list";
	}
	
	private void paging(SearchCondition condition, Model model, Pageable pageable) {
		
		Page<Notice> page = noticeService.searchNoticeList(pageable, condition);
		model.addAttribute("page", page);
		pager = new Pager(page.getSize(), pageGroupSize, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
	}
	
	@GetMapping("create")
	public String noticeInsertForm(Notice notice, Model model){
//		model.addAttribute(null, model)
		return "thymeleaf/notice/notice_create";
	}
	
	@PostMapping("create")
	public String inserNoticce(Notice notice, Principal principal) {
		log.info(notice.toString());
		noticeService.createNotice(notice, principal.getName());
		return "redirect:list";
	}
	
	@GetMapping("detail/{id}")
	public String noticeDetailForm(@PathVariable("id")Notice notice, Model model){
		noticeService.viewCountInc(notice);
		log.info("notice진입");
		model.addAttribute("noticeDetail", notice);
		return "thymeleaf/notice/notice_detail";
	}
	
	@GetMapping("update/{id}")
	public String noticeUpdateForm(@PathVariable("id")Notice notice, Model model){
		log.info("notice입력 페이지");
		model.addAttribute("noticeDetail", notice);
		log.info(notice.toString());
		
		return "thymeleaf/notice/notice_modify";
	}

	@PostMapping("update")
	public String updateNotice(Notice notice, Model model) {
		log.info("notice update");
		log.info(notice.toString());
		model.addAttribute("noticeDetail", notice);
		noticeService.updateNotice(notice);
		return "redirect:list";
	}
	
	@GetMapping("delete")
	public String deleteNotice(Long id) {
		log.info("notice delete");
		noticeService.deleteNotice(id);
		return "redirect:list";
	}
}
