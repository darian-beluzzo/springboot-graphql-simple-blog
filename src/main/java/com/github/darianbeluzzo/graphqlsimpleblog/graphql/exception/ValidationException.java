package com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception;

public class ValidationException extends GenericException {

    public ValidationException(final String message, final Object... params) {
	super(message, params);
    }

    public ValidationException(final String message, final String invalidField, final Object... params) {
	super(message, invalidField, params);
    }
}
