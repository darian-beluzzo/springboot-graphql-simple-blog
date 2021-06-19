package com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenericException extends RuntimeException implements GraphQLError {

    private String invalidField;

    public GenericException(final String message, final Object... params) {
	super(String.format(message, params));
    }

    public GenericException(final String message, final String invalidField, final Object... params) {
	this(message, params);
	this.invalidField = invalidField;
    }

    @Override
    public ErrorClassification getErrorType() {
	return ErrorType.ValidationError;
    }

    @Override
    public Map<String, Object> getExtensions() {
	return Collections.singletonMap("invalidField", invalidField);
    }

    @Override public List<SourceLocation> getLocations() {
	return null;
    }
}
