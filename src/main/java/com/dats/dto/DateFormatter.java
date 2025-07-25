package com.dats.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateFormatter {
	
	public static final Logger logger = LoggerFactory.getLogger(DateFormatter.class);
	
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	static DateTimeFormatter timeFormatterHMS = DateTimeFormatter.ofPattern("hh:mm:ss");
	
	//static DateTimeFormatter timeFormatterHM = DateTimeFormatter.ofPattern("HH:mm a");
	
	public LocalDateTime formatToDateTime(String dateTimeString) {
		/** check that the string parameter is not null and not empty*/
		if(dateTimeString != null && !dateTimeString.isEmpty() ) {
			/** parse date time String into LocalDateTime object with given format*/
			return LocalDateTime.parse(dateTimeString, this.dateTimeFormatter);
		}
		else return null; 
	}
	
	public LocalDate formatToDate(String dateString) {
		/** check that the string parameter is not null and not empty*/
		if(dateString != null && !dateString.isEmpty() ) {
			/** parse date String into LocalDate object with given format*/
			return LocalDate.parse(dateString, this.dateFormatter);
		}
		else return null; 
	}
	
	public static LocalTime formatToTimeHMS(String timeString) {
		/** check that the string parameter is not null and not empty*/
		if(timeString != null && !timeString.isEmpty() ) {
			/** parse date time String into LocalDateTime object with given format*/
			return LocalTime.parse(timeString, timeFormatterHMS);
		}
		else return null; 
	}
	
	
	public LocalTime formatToTimeHM(String timeString) {
		/** check that the string parameter is not null and not empty*/
		if(timeString != null && !timeString.isEmpty() ) {
			/** parse date time String into LocalDateTime object with given format*/
			DateTimeFormatter timeFormatterHM = DateTimeFormatter.ofPattern("HH:mm a");
			//return LocalTime.parse(timeString, timeFormatterHM);
			return LocalTime.parse(timeString, timeFormatterHM);
		}
		else return null; 
	}
	
	
	public LocalTime formatAMPMToTimeHM(String timeStringWithAMPM) {
		/** check that the string parameter is not null and not empty*/
		if(timeStringWithAMPM != null && !timeStringWithAMPM.isEmpty() ) {
			/** remove AM/PM and extra whitespace from time string*/
			String timeStr = timeStringWithAMPM.replace("AM", "").replace("PM", "")
								.replace("am", "").replace("pm", "").trim();
			/** parse time string into LocalTime and return*/
			LocalTime convertedTime = LocalTime.parse(timeStr);
			logger.info("convertedTime:\t" + convertedTime);
			return convertedTime;
		}
		else return null; 
	}
	
}
