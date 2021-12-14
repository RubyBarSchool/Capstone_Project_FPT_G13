package com.university.fpt.acf;

import com.university.fpt.acf.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = "com.university.fpt.acf")
public class AcfApplication{

	@Autowired
	private FileStorageService fileStorageService;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		SpringApplication.run(AcfApplication.class, args
		);
	}


	@Bean
	public void createDirectory() {
		fileStorageService.init();
	}
}
