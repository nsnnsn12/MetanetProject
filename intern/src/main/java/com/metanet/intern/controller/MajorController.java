package com.metanet.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.Major;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.vo.MajorSearchCondition;
import com.metanet.intern.vo.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/major")
public class MajorController {
	@Autowired
	MajorService majorService;
	
	private Pager pager;
	private final int pageGroupSize = 5;
	private final int pageSize = 3;
	
	@GetMapping("create")
	public String majorInsertForm(Major major){
		return "thymeleaf/major/major_create";
	}
	
	@ResponseBody
	@PostMapping("create")
	public String inserMajor(Major major) {
		majorService.insertMajor(major);
		log.info(major.getTitle());
		return "<script>alert('등록되었습니다.');location.href='/major/list'</script>";
	}
	
	@GetMapping("list")
	public String list(@ModelAttribute("condition") MajorSearchCondition condition, @PageableDefault(sort = {"createDate"}, direction = Direction.DESC, size = pageSize)Pageable pageable, Model model) {
		paging(condition, model, pageable);
		return "thymeleaf/major/major_list";
	}
	
	@GetMapping("page/{pageNo}")
	public String search(@ModelAttribute("condition") MajorSearchCondition condition, @PathVariable("pageNo")int pageNo, Model model) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, "createDate"));
		paging(condition, model, pageable);
		return "thymeleaf/major/major_list";
	}
	
	private void paging(MajorSearchCondition condition, Model model, Pageable pageable) {
		Page<Major> page = majorService.searchList(pageable, condition);
		model.addAttribute("page", page);
		pager = new Pager(page.getSize(), pageGroupSize, (int)page.getTotalElements(), page.getNumber());
		model.addAttribute("pager", pager);
	}
	
	
	@GetMapping("detail/{id}")
	public String majorDetailForm(@PathVariable("id")Major major, Model model){
		log.info("detail진입");
		log.info(major.getTitle());
		log.info(major.getCode());
		model.addAttribute("detailObject", major);
		
		return "thymeleaf/major/major_detail";
	}
	
	@ResponseBody
	@PostMapping("update")
	public String updateMajor(Major major, Model model){
		log.info("update진입");
		log.info(major.getId().toString());
		majorService.updateMajor(major);
		return "<script>alert('수정되었습니다.');location.href='/major/detail/"+major.getId()+"'</script>";
	}
	
	@ResponseBody
	@GetMapping("delete")
	public String deleteMajor(Long id) {
		log.info("delete진입");
		majorService.deleteMajor(id);
		return "<script>alert('삭제되었습니다.');location.href='/major/list'</script>";
	}
}
