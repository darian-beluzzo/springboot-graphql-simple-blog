package com.github.darianbeluzzo.graphqlsimpleblog.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.Validate;

/**
 * @author darian.beluzzo
 * @version 1.0
 * @since 24/03/2021
 */
public class GraphQLTestUtils {

    public static ObjectNode createVariable(final String fieldName, final Object object, final String... fieldsToRemove) {
	Validate.notNull(object);

	final ObjectMapper objectMapper = new ObjectMapper();
	final JsonNode inputJson = objectMapper.convertValue(object, JsonNode.class);
	final ObjectNode variables = objectMapper.createObjectNode();
	variables.replace(fieldName, inputJson);
	for (String field : fieldsToRemove) {
	    ((ObjectNode) inputJson).remove(field);
	}

	return variables;
    }

}
