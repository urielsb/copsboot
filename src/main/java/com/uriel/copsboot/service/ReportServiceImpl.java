/**
 * 
 */
package com.uriel.copsboot.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uriel.copsboot.entities.Report;
import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;
import com.uriel.copsboot.repositories.ReportRepository;

/**
 * @author urielsb
 *
 */
@Service
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;
	private final UserService userService;
	
	@Autowired
	public ReportServiceImpl(ReportRepository repository, UserService userService) {
		this.reportRepository = repository;
		this.userService = userService;
	}
	
	@Override
	public Report createReport(UserId reporter, ZonedDateTime dateTime, String description) {
		User user = userService.getUser(reporter).get();
		Report report = new Report(reportRepository.nextId(), user, dateTime, description);
		return reportRepository.save(report);
	}

}
