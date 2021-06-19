package com.github.darianbeluzzo.graphqlsimpleblog.graphql.execution;

import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.execution.NonNullableFieldWasNullException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncTransactionalExecutionStrategy extends AsyncExecutionStrategy {

    @Transactional
    @Override
    public CompletableFuture<ExecutionResult> execute(final ExecutionContext executionContext,
		    final ExecutionStrategyParameters parameters) throws NonNullableFieldWasNullException {
	return super.execute(executionContext, parameters);
    }
}