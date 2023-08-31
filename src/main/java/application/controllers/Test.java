package application.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.payloads.ImagePayload;
import application.services.ImageService;

@RestController
@RequestMapping("/test")
public class Test {

	@Autowired
	ImageService imgser;

	@PostMapping("/upload")
	public ImagePayload register(@RequestBody MultipartFile payload) throws IOException {

		ImagePayload test = imgser.uploadImage(payload);

		return test;

	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam String idDelete) throws IOException {

		imgser.deleteImage(idDelete);

	}
}
