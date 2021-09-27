package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.EducationDivision;
import com.metanet.intern.enummer.Role;

public class EducationSpec {
	public static Specification<Education> likeTitle(String title) {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
        };
    }
	
	public static Specification<Education> likeCode(String code) {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("code"), "%" + code + "%");
            }
        };
    }
	
	public static Specification<Education> equalMajor(Major major) {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("major"), major);
            }
        };
    }
	
	public static Specification<Education> equalCredit(Integer credit) {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("credit"), credit);
            }
        };
    }
	
	public static Specification<Education> equalDivision(EducationDivision division) {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("division"), division);
            }
        };
    }
	
	public static Specification<Education> isNotDeleted() {
        return new Specification<Education>() {
            @Override
            public Predicate toPredicate(Root<Education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("isDeleted"), 0);
            }
        };
    }
}
