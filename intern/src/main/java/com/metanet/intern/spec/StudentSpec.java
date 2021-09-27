package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.Student;
import com.metanet.intern.enummer.Role;

public class StudentSpec {
	public static Specification<Student> likeName(String name) {
		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.like(root.get("name"), "%" + name + "%");
			}
		};
	}

	public static Specification<Student> likeStudentNumber(String studentNumber) {
		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.like(root.get("studentNumber"), "%" + Integer.valueOf(studentNumber.toString()) + "%");
			}
		};
	}
	
	public static Specification<Student> equalMajor(Major major) {
        return new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("major"), major);
            }
        };
    }

	public static Specification<Student> isNotDeleted() {
		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.equal(root.get("isDeleted"), 0);
			}
		};
	}
}
