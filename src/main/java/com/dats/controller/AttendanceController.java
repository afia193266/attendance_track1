package com.dats.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dats.entity.Attendance;
import com.dats.entity.AttendanceSummary;
import com.dats.entity.Section;
import com.dats.service.AttendanceSummaryService;

@Controller
public class AttendanceController {
	
	@Autowired AttendanceSummaryService attendanceSummaryService;
	
	public static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);
	
	@GetMapping("take-attendance.html")
	public String takeAttendance(Model m, @RequestParam Integer courseId, RedirectAttributes ra) {
		//LocalDate today = LocalDate.now();
		AttendanceSummary attendanceSheet = attendanceSummaryService.createAttendaceSheet(courseId);
		m.addAttribute("attendanceSheet", attendanceSheet);
		
		return "dats/attendance/take-attendance";
	}

	@Transactional
	@PostMapping("attendance-save")
	public String saveAttendance(@Valid AttendanceSummary attdSheet, Model m, RedirectAttributes rA) {	

		logger.info("Sheet: \t" + attdSheet);
		if(attdSheet != null && !attdSheet.getStudentList().isEmpty()) {
			attendanceSummaryService.saveAttendanceSummary(attdSheet);
		}
		
//		for(User user : attdSheet.getStudentList()) {
//			if(user != null && !user.toString().isBlank()) {
//				Attendance attendance = new Attendance();
//				attendance.setStudentId(user.getUsername());
//				attendance.setCourseId(attdSheet.getCourse().getCourseId());
//				attendance.setAttendance(user.isAttendance());
//				attendance.setAttdDateStr(attdSheet.getAttdDateStr());
//				logger.info("Attd: \t" + attendance);
//				attendanceService.saveAttendance(attendance);
//			}
//		}		
		
		return "redirect:/";
	}
	
	

	@GetMapping("attendance-report.html")
	public String attendanceReport(Model m, @RequestParam Integer courseId, RedirectAttributes ra) {
		//LocalDate today = LocalDate.now();
		AttendanceSummary attendanceReport = attendanceSummaryService.generateAttendanceReport(courseId);
		m.addAttribute("attendanceReport", attendanceReport);
		return "dats/attendance/attendance-report";
	}
	
	
	@GetMapping("attendance-report-student.html")
	public String attendanceReportStudent(Model m, @RequestParam Integer courseId, String studentId, RedirectAttributes ra) {
		//LocalDate today = LocalDate.now();
		
		AttendanceSummary attendanceReport = attendanceSummaryService
				.generateAttendanceReportStudent(courseId, studentId.toLowerCase());
		m.addAttribute("attendanceReport", attendanceReport);
		return "dats/attendance/attendance-report-student";
	}
	
	@GetMapping("attendance-details.html")
	public String showAttendanceDetailReport(Model m,  @RequestParam Integer courseId, String studentId, RedirectAttributes ra) {
		
		m.addAttribute("ad", attendanceSummaryService
				.generateDetailsAttendanceReport(courseId));
		return "dats/attendance/attendance-details";
	}
}
