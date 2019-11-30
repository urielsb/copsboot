/**
 * 
 */
package com.uriel.copsboot.report.web;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Uriel Santoyo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidCreateReportParameters
public class CreateReportParameters {

	private ZonedDateTime dateTime;
	@ValidReportDescription
	private String description;
	private boolean trafficIncident;
	private int numberOfInvolvedCars;
}
