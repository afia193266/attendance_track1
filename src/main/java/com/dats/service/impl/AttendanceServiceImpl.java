package com.dats.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dats.dto.CustomCommonUtils;
import com.dats.dto.User;
import com.dats.entity.Attendance;
import com.dats.entity.Course;
import com.dats.repo.AttendanceRepo;
import com.dats.service.AttendanceService;
import com.dats.service.CourseService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	@Autowired AttendanceRepo 		attendanceRepo;
	@Autowired SectionService 		sectionService;
	@Autowired UserService 			userService;
	@Autowired CourseService 		courseService;
	
	public static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
	



	@Transactional
	@Override
	public Attendance saveAttendance(Attendance attendance) {
		
		//CustomCommonUtils
		//attendance.setCreatedBy(new CustomCommonUtils().getCurrentUserName());
		//attendance.setAttdDate(LocalDate.now());
		
		// parse date strings into LocalDate object and set to corresponding fields
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		logger.info("Attendance:\t" + attendance);
//		if( attendance.getAttdDateStr() != null && !attendance.getAttdDateStr().isEmpty())
//			attendance.setAttdDate(LocalDate.parse(attendance
//					.getAttdDateStr(), formatter));
		
		
		logger.info("Before save:" + attendance);
		Attendance savedAttendance = attendanceRepo.saveAndFlush(attendance);
		logger.info("After save:" + savedAttendance);
		
		return savedAttendance;
	}


	@Override
	public List<Attendance> getAttendance(String studentId) {
		List<Attendance> attendance = attendanceRepo.findAllByStudentId(studentId);
		if(!attendance.isEmpty()) {
			return attendance;
		}
		else return null;
	}


//	@Override
//	public List<Attendance> getAttendance(Integer courseId) {
//		List<Attendance> attendance = attendanceRepo.findAllByCourseId(courseId);
//		if(!attendance.isEmpty()) {
//			return attendance;
//		}
//		else return null;
//	}
//
//
//	@Override
//	public List<Attendance> getAttendance(Integer courseId, String studentId) {
//		List<Attendance> attendance = attendanceRepo
//				.findAllByCourseIdAndStudentId(courseId, studentId);
//		if(!attendance.isEmpty()) {
//			return attendance;
//		}
//		else return null;
//	}

}
