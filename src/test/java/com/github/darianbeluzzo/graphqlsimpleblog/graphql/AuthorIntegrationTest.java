package com.github.darianbeluzzo.graphqlsimpleblog.graphql;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.darianbeluzzo.graphqlsimpleblog.SimpleBlogApplication;
import com.github.darianbeluzzo.graphqlsimpleblog.domain.Author;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.model.AuthorInput;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver.AddressGraphQL;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver.CommentGraphQL;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver.PostGraphQL;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.AuthorRepository;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.AuthorRepositoryTest;
import com.github.darianbeluzzo.graphqlsimpleblog.util.GraphQLTestErrorMessage;
import com.github.darianbeluzzo.graphqlsimpleblog.util.GraphQLTestUtils;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQLTest
@MockBean(classes = { AddressGraphQL.class, CommentGraphQL.class, PostGraphQL.class })
@ContextConfiguration(classes = SimpleBlogApplication.class)
public class AuthorIntegrationTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void testCreateAuthor() throws IOException {
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doReturn(expectedAuthor).when(authorRepository).save(any(Author.class));
	// Create the request input object
	final AuthorInput authorInput = new AuthorInput(expectedAuthor.getEmail(), null, expectedAuthor.getName(),
			expectedAuthor.getUsername());
	// Create the insert mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("input", authorInput, "id");
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/createAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.createAuthor.id")).isNotNull();
	assertThat(response.get("$.data.createAuthor.name")).isEqualTo(expectedAuthor.getName());
	assertThat(response.get("$.data.createAuthor.username")).isEqualTo(expectedAuthor.getUsername());
	assertThat(response.get("$.data.createAuthor.email")).isEqualTo(expectedAuthor.getEmail());
    }

    @Test
    public void testCreateAuthorThatAlreadyExists() throws IOException {
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doThrow(DataIntegrityViolationException.class).when(authorRepository).save(any(Author.class));
	// Create the request input object
	final AuthorInput authorInput = new AuthorInput(expectedAuthor.getEmail(), null, expectedAuthor.getName(),
			expectedAuthor.getUsername());
	// Create the insert mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("input", authorInput, "id");
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/createAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.createAuthor")).isNull();
	final GraphQLTestErrorMessage error = response.get("$.errors[0]", GraphQLTestErrorMessage.class);
	assertThat(error.getMessage()).isEqualTo("Author already exists");
	assertThat(error.getExtension("invalidField")).isEqualTo("username");
	assertThat(error.getExtension("classification")).isEqualTo("ValidationError");

    }

    @Test
    public void testCreateAuthorWithInvalidEmail() throws IOException {
	// TODO: Precisa implementar a validacao de email e tambem fazer o teste
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doReturn(expectedAuthor).when(authorRepository).save(any(Author.class));
	// Create the request input object
	final AuthorInput authorInput = new AuthorInput("user#email.com", null, expectedAuthor.getName(),
			expectedAuthor.getUsername());
	// Create the insert mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("input", authorInput, "id");
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/createAuthor.graphqls", variables);
	System.out.println(response.getRawResponse().getBody());
	// Check the response data
	//	assertThat(response.isOk()).isTrue();
	//	assertThat(response.get("$.data.createAuthor")).isNull();
	//	final GraphQLTestErrorMessage error = response.get("$.errors[0]", GraphQLTestErrorMessage.class);
	//	assertThat(error.getMessage()).isEqualTo("Author already exists");
	//	assertThat(error.getExtension("invalidField")).isEqualTo("username");
	//	assertThat(error.getExtension("classification")).isEqualTo("ValidationError");

    }

    @Test
    public void testDeleteAuthorById() throws IOException {
	// Configure mock repo to return the expected object
	doNothing().when(authorRepository).deleteById(any(Long.class));
	// Create the input object and call the graphql api
	final ObjectNode variables = GraphQLTestUtils.createVariable("id", 1L);
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/deleteAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.deleteAuthor")).isEqualTo("true");
    }

    @Test
    public void testDeleteAuthorByIdThatNotExists() throws IOException {
	// Configure mock repo to return the expected object
	doThrow(EmptyResultDataAccessException.class).when(authorRepository).deleteById(any(Long.class));
	// Create the input object and call the graphql api
	final ObjectNode variables = GraphQLTestUtils.createVariable("id", 1L);
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/deleteAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.deleteAuthor")).isNull();
	final GraphQLTestErrorMessage error = response.get("$.errors[0]", GraphQLTestErrorMessage.class);
	assertThat(error.getMessage()).isEqualTo("Author not exists");
	assertThat(error.getExtension("invalidField")).isEqualTo("id");
	assertThat(error.getExtension("classification")).isEqualTo("DataFetchingException");

    }

    @Test
    public void testFindAuthorById() throws IOException {
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doReturn(Optional.of(expectedAuthor)).when(authorRepository).findById(expectedAuthor.getId());
	// Create the insert mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("id", expectedAuthor.getId());
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/getAuthorById.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.getAuthorById.id")).isNotNull();
	assertThat(response.get("$.data.getAuthorById.name")).isEqualTo(expectedAuthor.getName());
	assertThat(response.get("$.data.getAuthorById.username")).isEqualTo(expectedAuthor.getUsername());
	assertThat(response.get("$.data.getAuthorById.email")).isEqualTo(expectedAuthor.getEmail());
    }

    @Test
    public void testFindAuthorByIdThatNotExists() throws IOException {
	// Configure mock repo to return the expected object
	doReturn(Optional.empty()).when(authorRepository).findById(any(Long.class));
	// Create the insert mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("id", -1);
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/getAuthorById.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.getAuthorById")).isNull();
	final GraphQLTestErrorMessage error = response.get("$.errors[0]", GraphQLTestErrorMessage.class);
	assertThat(error.getMessage()).isEqualTo("Author not exists");
	assertThat(error.getExtension("invalidField")).isEqualTo("id");
	assertThat(error.getExtension("classification")).isEqualTo("DataFetchingException");
    }

    @Test
    public void testFindAuthors() throws IOException {
	final Author expectedAuthor1 = AuthorRepositoryTest.createRamdomAuthorWithId();
	final Author expectedAuthor2 = AuthorRepositoryTest.createRamdomAuthorWithId();
	final List<Author> expectedAuthors = Arrays.asList(expectedAuthor1, expectedAuthor2);
	// Configure mock repo to return the expected object
	doReturn(expectedAuthors).when(authorRepository).findAll();
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/author/listAuthors.graphqls");
	// Check the response data
	assertThat(response.isOk()).isTrue();
	final List<Author> result = response.getList("$.data.listAuthors", Author.class);
	assertThat(result).hasSameElementsAs(expectedAuthors);
    }

    @Test
    public void testUpdateAuthor() throws IOException {
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doReturn(expectedAuthor).when(authorRepository).save(any(Author.class));
	doReturn(true).when(authorRepository).existsById(any(Long.class));
	// Create the request input object
	final AuthorInput authorInput = new AuthorInput(expectedAuthor.getEmail(), expectedAuthor.getId(),
			expectedAuthor.getName(), null);
	// Create the update mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("input", authorInput, "username");
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/updateAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.updateAuthor.id", Long.class)).isEqualTo(expectedAuthor.getId());
	assertThat(response.get("$.data.updateAuthor.name")).isEqualTo(expectedAuthor.getName());
	assertThat(response.get("$.data.updateAuthor.username")).isEqualTo(expectedAuthor.getUsername());
	assertThat(response.get("$.data.updateAuthor.email")).isEqualTo(expectedAuthor.getEmail());
    }

    @Test
    public void testUpdateAuthorThatNotExists() throws IOException {
	final Author expectedAuthor = AuthorRepositoryTest.createRamdomAuthorWithId();
	// Configure mock repo to return the expected object
	doReturn(expectedAuthor).when(authorRepository).save(any(Author.class));
	// Create the request input object
	final AuthorInput authorInput = new AuthorInput(expectedAuthor.getEmail(), expectedAuthor.getId(),
			expectedAuthor.getName(), null);
	// Create the update mutation removing the id field
	final ObjectNode variables = GraphQLTestUtils.createVariable("input", authorInput, "username");
	// Call GraphQL API and get response
	GraphQLResponse response = graphQLTestTemplate.perform("graphql/author/updateAuthor.graphqls", variables);
	// Check the response data
	assertThat(response.isOk()).isTrue();
	assertThat(response.get("$.data.updateAuthor")).isNull();
	final GraphQLTestErrorMessage error = response.get("$.errors[0]", GraphQLTestErrorMessage.class);
	assertThat(error.getMessage()).isEqualTo("Author not exists");
	assertThat(error.getExtension("invalidField")).isEqualTo("id");
	assertThat(error.getExtension("classification")).isEqualTo("DataFetchingException");
    }

}