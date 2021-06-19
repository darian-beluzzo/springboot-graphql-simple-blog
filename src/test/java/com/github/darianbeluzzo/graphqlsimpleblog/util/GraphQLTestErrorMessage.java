package com.github.darianbeluzzo.graphqlsimpleblog.util;

import lombok.Data;

import java.util.Map;

@Data
public class GraphQLTestErrorMessage {

    private String Message;

    private Map<String, String> extensions;

    public String getExtension(final String name) {
	return extensions.get(name);
    }
}
