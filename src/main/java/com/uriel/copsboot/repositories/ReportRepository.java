/**
 * 
 */
package com.uriel.copsboot.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.uriel.copsboot.entities.Report;

/**
 * @author urielsb
 *
 */
public interface ReportRepository extends CrudRepository<Report, UUID>, ReportRepositoryCustom {

}
