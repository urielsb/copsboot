/**
 * 
 */
package com.uriel.copsboot.repositories;

import java.util.UUID;

import com.uriel.copsboot.entities.ReportId;

/**
 * @author Uriel Santoyo
 *
 */
public class ReportRepositoryImpl implements ReportRepositoryCustom {

	@Override
	public ReportId nextId() {
		return new ReportId(UUID.randomUUID());
	}

}
