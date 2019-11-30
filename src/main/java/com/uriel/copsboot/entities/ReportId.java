/**
 * 
 */
package com.uriel.copsboot.entities;

import java.util.UUID;

/**
 * @author Uriel Santoyo
 *
 */
public class ReportId extends AbstractEntityId<UUID> {
	
	protected ReportId() {}
	
	public ReportId(UUID id) {
		super(id);
	}
}
