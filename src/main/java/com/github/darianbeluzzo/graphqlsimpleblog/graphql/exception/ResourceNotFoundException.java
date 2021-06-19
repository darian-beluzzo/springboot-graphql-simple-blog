package com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;

public class ResourceNotFoundException extends GenericException {

    public ResourceNotFoundException(final String message, final Object... params) {
	super(message, params);
    }

    public ResourceNotFoundException(final String message, final String invalidField, final Object... params) {
	super(message, invalidField, params);
    }

    @Override public ErrorClassification getErrorType() {
	return ErrorType.DataFetchingException;
    }

}
