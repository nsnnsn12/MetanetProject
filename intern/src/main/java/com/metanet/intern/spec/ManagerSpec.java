package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.Role;

public class ManagerSpec {
	public static Specification<Manager> likeName(String name) {
        return new Specification<Manager>() {
            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }
	
	public static Specification<Manager> likeLoginId(String loginId) {
        return new Specification<Manager>() {
            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("loginId"), "%" + loginId + "%");
            }
        };
    }
	
	public static Specification<Manager> equalRole(Role role) {
        return new Specification<Manager>() {
            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("role"), role);
            }
        };
    }
	
	public static Specification<Manager> equalAccept(Integer isAccept) {
        return new Specification<Manager>() {
            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("isAccept"), isAccept);
            }
        };
    }
	
	public static Specification<Manager> isNotDeleted() {
        return new Specification<Manager>() {
            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("isDeleted"), 0);
            }
        };
    }
}
