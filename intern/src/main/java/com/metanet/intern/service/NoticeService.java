package com.metanet.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Notice;
import com.metanet.intern.repository.NoticeRepository;
import com.metanet.intern.spec.NoticeSpec;
import com.metanet.intern.vo.SearchCondition;

@Service
@Transactional
public class NoticeService {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	final Integer notDeleted = 0;
	
	public Page<Notice> searchNoticeList(Pageable pageable, SearchCondition condition) {
		// //삭제되지 않은 정보
		Specification<Notice> spec = Specification.where(NoticeSpec.isNotDeleted());
		if(!condition.getType().isBlank() && !condition.getText().isBlank()) {
			if(condition.getType().equals("title")) {
				spec = spec.and(NoticeSpec.likeTitle(condition.getText()));
			}else {
				spec = spec.and(NoticeSpec.likeWriter(condition.getText()));
			}
		}
		
		
		return noticeRepository.findAll(spec, pageable);
	}
	
	public void updateNotice(Notice update) {
		Notice notice = noticeRepository.findById(update.getId()).get();
		notice.setNoticeTitle(update.getNoticeTitle());
		notice.setContent(update.getContent());
		noticeRepository.save(notice);
	}
	
	public void createNotice(Notice notice, String wirterId) {
		notice.setWriter(wirterId);
		notice.setViewcount(0);
		noticeRepository.save(notice);
	}

	public void deleteNotice(Long id) {
		Notice notice = noticeRepository.findById(id).get();
		notice.setIsDeleted(1);
		noticeRepository.save(notice);
	}
	
	public Page<Notice> getAllList(Pageable pageable) {
		return noticeRepository.findByIsDeleted(notDeleted, pageable);
	}

	public void viewCountInc(Notice notice) {
		notice.setViewcount(notice.getViewcount()+1);
		noticeRepository.save(notice);
		
	}


}
