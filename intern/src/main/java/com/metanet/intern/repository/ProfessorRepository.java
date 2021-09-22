package com.metanet.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
}
