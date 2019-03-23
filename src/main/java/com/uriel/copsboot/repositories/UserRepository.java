/**
 * 
 */
package com.uriel.copsboot.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.uriel.copsboot.entities.User;

/**
 * @author Uriel Santoyo
 *
 */
public interface UserRepository extends CrudRepository<User, UUID> {}
