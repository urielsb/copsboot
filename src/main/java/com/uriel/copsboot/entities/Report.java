/**
 * 
 */
package com.uriel.copsboot.entities;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Uriel Santoyo
 *
 */
@Entity
public class Report extends AbstractEntity<ReportId> {

	@ManyToOne
	private User reporter;
	private ZonedDateTime dateTime;
	private String description;
	
	public Report(ReportId id, User reporter, ZonedDateTime dateTime, String description) {
		super(id);
		this.reporter = reporter;
		this.dateTime = dateTime;
		this.description = description;
	}
	
	public User getReporter() {
		return reporter;
	}
	
	public void setReporter(User reporter) {
		this.reporter = reporter;
	}
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
