package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Notice;

public class NoticeSpec {

	public static Specification<Notice> likeTitle(String noticetitle) {
        return new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("noticeTitle"), "%" + noticetitle + "%");
            }
        };
    }
	
	public static Specification<Notice> likeWriter(String writer) {
        return new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("writer"), "%" + writer + "%");
            }
        };
    }

	
	public static Specification<Notice> isNotDeleted() {
        return new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("isDeleted"), 0);
            }
        };
    }
}
