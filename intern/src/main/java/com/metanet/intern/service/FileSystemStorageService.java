package com.metanet.intern.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.intern.config.StorageProperties;
import com.metanet.intern.domain.PhotoFile;
import com.metanet.intern.exception.StorageException;
import com.metanet.intern.exception.StorageFileNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation()).resolve("photo");
		
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}
	
	@Override
	public PhotoFile photoStore(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return null;
			}
			PhotoFile photoFile = new PhotoFile();
			
			photoSetting(photoFile, file);
			
			//uploadDir/photo/2304234_문서작성목록
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(photoFile.getSaveFileName()))
					.normalize().toAbsolutePath();
			
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				return null;
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			return photoFile;
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	public void photoSetting(PhotoFile photoFile, MultipartFile file) {
		//오리지널 네임
		photoFile.setOriginalFileName(file.getOriginalFilename());
		//파일 확장자 (img, png 등등)
		photoFile.setMimeType(file.getContentType());
		//파일 사이즈
		photoFile.setFileSize(file.getSize());
		log.info(photoFile.getOriginalFileName());
		
		//파일 이름 중복 방지용 업로드 시간 문자열
		SimpleDateFormat format1 = new SimpleDateFormat( "yyyyMMddHHmmss");
		Date time = new Date();
		String fileSuffix = format1.format(time);
		log.info(file.getName());
		
		//작성문서목록_202102342340.png
		String[] fileSplit = photoFile.getOriginalFileName().split("\\.");
		log.info(fileSplit[0]);
		
		//실제 서버 저장명
		photoFile.setSaveFileName(fileSplit[0]+"_"+fileSuffix+"."+fileSplit[1]);
		
		//저장파일 path
		photoFile.setFilePath(this.rootLocation.toString());
		
	}
	
	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
