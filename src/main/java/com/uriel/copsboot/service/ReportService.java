package com.uriel.copsboot.service;

import java.time.ZonedDateTime;

import com.uriel.copsboot.entities.Report;
import com.uriel.copsboot.entities.UserId;

/**
 * 
 * @author Uriel Santoyo
 *
 */
public interface ReportService {

	Report createReport(UserId reporter, ZonedDateTime dateTime, String description);
}
