package com.dats;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DATS_App {

	public static void main(String[] args) {
		SpringApplication.run(DATS_App.class, args);
	}

	/*
	 * @PostConstruct public void init(){ // Setting Spring Boot SetTimeZone
	 * TimeZone.setDefault(TimeZone.getTimeZone("UTC")); }
	 */
    
}
