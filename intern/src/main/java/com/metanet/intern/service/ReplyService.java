package com.metanet.intern.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Notice;
import com.metanet.intern.domain.Reply;
import com.metanet.intern.repository.ManagerRepository;
import com.metanet.intern.repository.NoticeRepository;
import com.metanet.intern.repository.ReplyRepository;

@Service
@Transactional
public class ReplyService {
	
	private final int delete = 1, notDelete = 0;
	
	@Autowired
	ReplyRepository replyRepository;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	NoticeRepository noticeRepository;

	public List<Reply> addReply(String content, Long noticeId, String name) {
		Reply reply = new Reply();
		//manager 정보 불러오기
		reply.setManager(managerRepository.findByLoginIdAndIsDeleted(name, notDelete));
		//공지사항 정보 불러오기
		Notice notice = noticeRepository.findById(noticeId).get();
		reply.setNotice(notice);
		//댓글 객체 생성해서 저장
		reply.setContents(content);
		replyRepository.save(reply);
		//good
		return notice.getReplies();
	}

	public List<Reply> delete(Long replyId, Long noticeId) {
		Notice notice = noticeRepository.findById(noticeId).get();
		notice.getReplies().remove(replyRepository.getById(replyId));
		
		replyRepository.deleteById(replyId);
		return notice.getReplies();
	}

	public List<Reply> list(Long noticeId) {
		Notice notice = noticeRepository.findById(noticeId).get();
		return notice.getReplies();
	}

	public List<Reply> updateReply(String content, Long replyId) {
		Reply reply = replyRepository.findById(replyId).get();
		reply.setContents(content);
		return reply.getNotice().getReplies();
	}
	
	
}
