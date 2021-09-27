package com.metanet.intern.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.intern.domain.Manager;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.service.ManagerService;
import com.metanet.intern.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileController {
	@Autowired
	StorageService storageService;
	
	@Autowired
	ManagerService managerService;
	
	@GetMapping("download/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id")PhotoFile photoFile) {
		Resource file = storageService.loadAsResource(photoFile.getSaveFileName());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@GetMapping("profileImage")
	@ResponseBody
	public ResponseEntity<Resource> profileImage(Principal principal) {
		log.info(principal.getName());
		log.info(managerService.findOne(principal.getName()).toString());
		Manager manager = managerService.findOne(principal.getName());
		if(manager.getPhoto() == null) return ResponseEntity.notFound().build();
		Resource file = storageService.loadAsResource(manager.getPhoto().getSaveFileName());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
}
