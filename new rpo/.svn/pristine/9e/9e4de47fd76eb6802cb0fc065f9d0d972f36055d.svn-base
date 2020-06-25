package com.resume.parser.controller.in;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.resume.parser.ResponseWrapper;
import com.resume.parser.service.in.ParserService;

@RestController
@CrossOrigin
public class ParserController {

	@Autowired
	private ParserService parserService;

	@PostMapping("/upload")
	public ResponseWrapper parseResume(@RequestParam MultipartFile resume) {
		ResponseWrapper responseWrapper = null;

		try {
			System.out.println("controller" + LocalDateTime.now());
			responseWrapper = parserService.parseResume(resume);
		} catch (Exception ex) {
			responseWrapper = new ResponseWrapper();
			responseWrapper.setMessage(ex.getMessage());
			responseWrapper.setStatus(500);
			ex.printStackTrace();
		}
		return responseWrapper;
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseWrapper handleMultipartException(Exception ex) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setData("No file uploaded");
		responseWrapper.setMessage("Please upload Resume!!");
		responseWrapper.setStatus(400);
		return responseWrapper;
	}

	@Bean
	private ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setBeanName("resumeParser");
		threadPoolTaskExecutor.setMaxPoolSize(25);
		threadPoolTaskExecutor.setQueueCapacity(80);
		threadPoolTaskExecutor.initialize();
		threadPoolTaskExecutor.destroy();
		return threadPoolTaskExecutor;
	}
}
