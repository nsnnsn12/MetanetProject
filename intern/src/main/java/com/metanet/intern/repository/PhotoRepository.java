package com.metanet.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metanet.intern.domain.PhotoFile;

public interface PhotoRepository extends JpaRepository<PhotoFile,Long>{
}
