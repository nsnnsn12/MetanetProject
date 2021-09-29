package com.metanet.intern.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.Reply;
import com.metanet.intern.service.MajorService;
import com.metanet.intern.service.ReplyService;
import com.metanet.intern.vo.MajorSearchCondition;
import com.metanet.intern.vo.Pager;
import com.metanet.intern.vo.StudentSearchCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	ReplyService replyService;
	
	@GetMapping("create")
	@ResponseBody
	public List<Reply> addReply(String content, Long noticeId, Principal principal){
		log.info("content확인:"+content);
//		for(Reply reply : replyService.addReply(content, noticeId, principal.getName())) {
//			log.info(reply.getContents());
//		}
		//댓글 내용, 작성자, 게시물
		return replyService.addReply(content, noticeId, principal.getName());
	}
	
	@GetMapping("delete")
	@ResponseBody
	public List<Reply> delete(Long replyId, Long noticeId) {
		return replyService.delete(replyId, noticeId);
	}
	
	@GetMapping("list")
	@ResponseBody
	public List<Reply> list(Long noticeId) {
		return replyService.list(noticeId);
	}
	
	@GetMapping("update")
	@ResponseBody
	public List<Reply> updateReply(String content, Long replyId){
		log.info("content확인:"+content);

		return replyService.updateReply(content, replyId);
	}
}
