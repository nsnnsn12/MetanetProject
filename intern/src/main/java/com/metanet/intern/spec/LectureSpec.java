package com.metanet.intern.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metanet.intern.domain.Education;
import com.metanet.intern.domain.Lecture;
import com.metanet.intern.domain.Major;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.enummer.EducationDivision;
import com.metanet.intern.enummer.Role;

public class LectureSpec {
	public static Specification<Lecture> likeProfessor(String professorName) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Manager> m = root.join("professor", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.like(m.get("name"), "%" + professorName + "%");
            }
        };
    }
	
	public static Specification<Lecture> likeTitle(String title) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Education> m = root.join("education", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.like(m.get("title"), "%" + title + "%");
            }
        };
    }
	
	public static Specification<Lecture> likeCode(String code) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Education> m = root.join("education", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.like(m.get("code"), "%" + code + "%");
            }
        };
    }
	
	public static Specification<Lecture> equalMajor(Major major) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Education> m = root.join("education", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.equal(m.get("major"), major);
            }
        };
    }
	
	public static Specification<Lecture> equalCredit(Integer credit) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Education> m = root.join("education", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.equal(m.get("credit"), credit);
            }
        };
    }
	
	public static Specification<Lecture> equalDivision(EducationDivision division) {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Join<Lecture, Education> m = root.join("education", JoinType.INNER);
            	// 2) like
                return criteriaBuilder.equal(m.get("division"), division);
            }
        };
    }

	public static Specification<Lecture> isNotDeleted() {
        return new Specification<Lecture>() {
            @Override
            public Predicate toPredicate(Root<Lecture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.equal(root.get("isDeleted"), 0);
            }
        };
    }
	
	
}
