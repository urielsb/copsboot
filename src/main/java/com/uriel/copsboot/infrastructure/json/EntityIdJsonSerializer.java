/**
 * 
 */
package com.uriel.copsboot.infrastructure.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.uriel.copsboot.entities.EntityId;

/**
 * @author Uriel Santoyo
 *
 */
@SuppressWarnings("rawtypes")
@JsonComponent
public class EntityIdJsonSerializer extends JsonSerializer<EntityId> {

	@Override
	public void serialize(EntityId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.asString());
	}

}
