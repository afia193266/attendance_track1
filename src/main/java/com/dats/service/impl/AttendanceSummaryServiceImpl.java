package com.dats.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dats.dto.AttendanceDetails;
import com.dats.dto.CustomCommonUtils;
import com.dats.dto.User;
import com.dats.entity.Attendance;
import com.dats.entity.AttendanceSummary;
import com.dats.entity.Course;
import com.dats.repo.AttendanceRepo;
import com.dats.repo.AttendanceSummaryRepo;
import com.dats.service.AttendanceService;
import com.dats.service.AttendanceSummaryService;
import com.dats.service.CourseService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Service
public class AttendanceSummaryServiceImpl implements AttendanceSummaryService{
	
	@Autowired AttendanceSummaryRepo 	attendanceSummaryRepo;
	@Autowired AttendanceRepo 			attendanceRepo;
	@Autowired SectionService 			sectionService;
	@Autowired UserService 				userService;
	@Autowired CourseService 			courseService;
	@Autowired AttendanceService 		attendanceService;
	
	public static final Logger logger = LoggerFactory.getLogger(AttendanceSummaryServiceImpl.class);

	@Override
	public AttendanceSummary createAttendaceSheet(Integer courseId) {
		Course course = courseService.getCourse(courseId);
		List<User> students = courseService.getStudentsByCourse(courseId);
		AttendanceSummary attendanceSummary = new AttendanceSummary();
		attendanceSummary.setCourse(course);
		attendanceSummary.setCourseId(course.getCourseId());
		attendanceSummary.setStudentList(students);
		attendanceSummary.setTotalClass(attendanceSummaryRepo.
				sumTotalClass(courseId));
		//attendance.setAttdDate(attdDate);
			
		return attendanceSummary;
	}
	
	@Transactional @Override
	public AttendanceSummary saveAttendanceSummary(AttendanceSummary attendanceSummary) {
		
		//CustomCommonUtils
		attendanceSummary.setCreatedBy(new CustomCommonUtils().getCurrentUserName());
		//attendance.setAttdDate(LocalDate.now());
		
		// parse date strings into LocalDate object and set to corresponding fields
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		logger.info("Attendance:\t" + attendanceSummary);
		if( attendanceSummary.getAttdDateStr() != null && 
				!attendanceSummary.getAttdDateStr().isEmpty())
			attendanceSummary.setAttdDate(LocalDate.parse(attendanceSummary
					.getAttdDateStr(), formatter));
		
		
		logger.info("Before save:" + attendanceSummary);
		AttendanceSummary savedAttendanceSummary = 
				attendanceSummaryRepo.saveAndFlush(attendanceSummary);
		logger.info("After save:" + savedAttendanceSummary);
		
		/** save nth times where n= no of classes*/
		Integer noOfClass = savedAttendanceSummary.getClasses();
		while(noOfClass > 0 ) {
			for(User user : attendanceSummary.getStudentList()) {
				if(user != null && !user.toString().isBlank()) {
					Attendance attendance = new Attendance();
					attendance.setStudentId(user.getUsername());
					attendance.setCourseId(savedAttendanceSummary.getCourseId());
					attendance.setAttdId(savedAttendanceSummary.getAttdId());
					attendance.setAttendance(user.isAttendance());
					//attendance.setAttdDateStr(attdSheet.getAttdDateStr());
					logger.info("Attd: \t" + attendance);
					attendanceService.saveAttendance(attendance);
				}
			}
			noOfClass--;
		}
		
		return savedAttendanceSummary;
	}

	@Override
	public List<AttendanceSummary> getAttendanceSummary(String studentId) {
//		List<AttendanceSummary> attendance = attendanceSummaryRepo.findAllByStudentId(studentId);
//		if(!attendance.isEmpty()) {
//			return attendance;
//		}
//		else 
			return null;
	}

	@Override
	public AttendanceSummary generateAttendanceReport(Integer courseId) {
		Course course = courseService.getCourse(courseId);
		List<User> students = courseService.getStudentsByCourse(courseId);
		
		AttendanceSummary attendanceSummary = new AttendanceSummary();
		attendanceSummary.setCourse(course);
		attendanceSummary.setCourseId(course.getCourseId());
		attendanceSummary.setStudentList(students);
		Integer totalClass = attendanceSummaryRepo.sumTotalClass(courseId);
		attendanceSummary.setTotalClass(totalClass);
		
		List<Attendance> attendanceSheet = new ArrayList<Attendance>();
		
		for(User student : students) {
			Attendance attendance = new Attendance();
			attendance.setStudentId(student.getUsername());
			Integer totalPresent = attendanceRepo.sumAttendanceByStudentIdAndCourseId(
					student.getUsername(), attendanceSummary.getCourseId());
			Float percentageFloat = (totalPresent.floatValue()/totalClass)*100;
			String percentage = String.format("%.02f", percentageFloat);
			attendance.setTotalPresent(totalPresent);
			attendance.setPercentage(percentage);
			attendanceSheet.add(attendance);
		}
		
		attendanceSummary.setAttendanceList(attendanceSheet);
		
		return attendanceSummary;
	}
	


	@Override
	public AttendanceSummary generateAttendanceReportStudent(Integer courseId, String studentId) {
		Course course = courseService.getCourse(courseId);
		//List<User> students = courseService.getStudentsByCourse(courseId);
		
		AttendanceSummary attendanceSummary = new AttendanceSummary();
		attendanceSummary.setCourse(course);
		attendanceSummary.setCourseId(course.getCourseId());
		//attendanceSummary.setStudentList(students);
		Integer totalClass = attendanceSummaryRepo.sumTotalClass(courseId);
		attendanceSummary.setTotalClass(totalClass);
		
		List<Attendance> attendanceSheet = new ArrayList<Attendance>();
		
		//for(User student : students) {
			Attendance attendance = new Attendance();
			attendance.setStudentId(studentId);
			Integer totalPresent = attendanceRepo.sumAttendanceByStudentIdAndCourseId(
					studentId, courseId);
			Float percentageFloat = (totalPresent.floatValue()/totalClass)*100;
			String percentage = String.format("%.02f", percentageFloat);
			attendance.setTotalPresent(totalPresent);
			attendance.setPercentage(percentage);
			attendanceSheet.add(attendance);
		//}
		
		attendanceSummary.setAttendanceList(attendanceSheet);
		
		return attendanceSummary;
	}

	@Override
	public AttendanceDetails generateDetailsAttendanceReport(Integer courseId) {
		Course course = courseService.getCourse(courseId);
		List<User> students = courseService.getStudentsByCourse(courseId);
		
		AttendanceSummary attendanceSummary = new AttendanceSummary();
		attendanceSummary.setCourse(course);
		attendanceSummary.setCourseId(course.getCourseId());
		attendanceSummary.setStudentList(students);
		Integer totalClass = attendanceSummaryRepo.sumTotalClass(courseId);
		attendanceSummary.setTotalClass(totalClass);
		
		List<AttendanceSummary> attendanceSummaryList = 
				attendanceSummaryRepo.findAllByCourseId(courseId);
		
		List<Attendance> attendanceSheet = new ArrayList<Attendance>();
		
//		for(User student : students) {
//			Attendance attendance = new Attendance();
//			attendance.setStudentId(student.getUsername());
//			Integer totalPresent = attendanceRepo.sumAttendanceByStudentIdAndCourseId(
//					student.getUsername(), attendanceSummary.getCourseId());
//			Float percentageFloat = (totalPresent.floatValue()/totalClass)*100;
//			String percentage = String.format("%.02f", percentageFloat);
//			attendance.setTotalPresent(totalPresent);
//			attendance.setPercentage(percentage);
//			attendanceSheet.add(attendance);
//		}
		
		//attendanceSummary.setAttendanceList(attendanceSheet);
		
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		attendanceDetails.setAtdSumList(attendanceSummaryList);
		attendanceDetails.setStdList(students);
		
		return attendanceDetails;
	}

}
