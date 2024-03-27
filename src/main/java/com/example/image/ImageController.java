package com.example.image;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/addImage")
public class ImageController {

	

	@Value("${disk.upload.basepath}")
	private String BASEPATH;
	
	@PostMapping			
	public String store(@RequestPart MultipartFile file) {
		System.out.println(file.getOriginalFilename());
//		String ext=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//		System.out.println(ext);
//		String fileName = ext;
		System.out.println(file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		File filePath = new File(BASEPATH, fileName);
		
		try(FileOutputStream out = new FileOutputStream(filePath)) {
			FileCopyUtils.copy(file.getInputStream(), out);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping
	public List<String> loadAll() {
		File dirPath = new File(BASEPATH);
		return Arrays.asList(dirPath.list());
	}
//	public Resource load(@RequestParam String fileName) {
//		File filePath = new File(BASEPATH, fileName);
//		if(filePath.exists())
//			return new FileSystemResource(filePath);
//		return null;
//	}

	
}
