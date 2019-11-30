/**
 * 
 */
package com.uriel.copsboot.report.web;

import java.time.ZonedDateTime;

import com.uriel.copsboot.entities.Report;
import com.uriel.copsboot.entities.ReportId;

import lombok.Value;

/**
 * @author Uriel Santoyo
 *
 */
@Value
public class ReportDto {

	private final ReportId id;
	private final String reporter;
	private final ZonedDateTime dateTime;
	private final String description;
	
	public static ReportDto fromReport(Report report) {
		return new ReportDto(report.getId(), 
				report.getReporter().getEmail(), 
				report.getDateTime(), 
				report.getDescription());
	}
	
}
