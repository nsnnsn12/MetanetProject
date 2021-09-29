package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.Major;
import com.metanet.intern.enummer.Role;

public class MajorSpec {
	public static Specification<Major> likeCode(String code) {
		return new Specification<Major>() {
			@Override
			public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.like(root.get("code"), "%" + code + "%");
			}
		};
	}

	public static Specification<Major> likeTitle(String title) {
		return new Specification<Major>() {
			@Override
			public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.like(root.get("title"), "%" + title + "%");
			}
		};
	}


	public static Specification<Major> isNotDeleted() {
		return new Specification<Major>() {
			@Override
			public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.equal(root.get("isDeleted"), 0);
			}
		};
	}
}
