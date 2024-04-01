package com.example.image;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	@GetMapping("img/files")
	public Resource getfile(@RequestParam String filename) {
		File filePath = new File(BASEPATH, filename);
//		System.out.println(filePath);
		try {
			Resource resource = new UrlResource(filePath.toURI());
			return resource;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			throw new RuntimeException("Could not read the file!");
		}
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
