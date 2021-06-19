package com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver;

import com.github.darianbeluzzo.graphqlsimpleblog.domain.Author;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception.ResourceNotFoundException;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception.ValidationException;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.model.AuthorInput;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.AuthorRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    public AuthorGraphQL(final AuthorRepository authorRepository) {
	this.authorRepository = authorRepository;
    }

    public Author createAuthor(final AuthorInput author) {
	final Author convertedAuthor = mapper.map(author, Author.class);
	try {
	    return this.authorRepository.save(convertedAuthor);
	} catch (DataIntegrityViolationException e) {
	    throw new ValidationException("Author with given username %s already exists", "username", author.getUsername());
	}
    }

    public boolean deleteAuthor(final Long id) {
	try {
	    this.authorRepository.deleteById(id);
	} catch (EmptyResultDataAccessException e) {
	    throw new ResourceNotFoundException("Author with given id %s not exists", "id", id);
	}
	return true;
    }

    public Author getAuthorById(final Long id) {
	return this.authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author with given id %s not exists", "id", id));
    }

    public List<Author> listAuthors() {
	return this.authorRepository.findAll();
    }

    public Author updateAuthor(final AuthorInput author) {
	final Author convertedAuthor = mapper.map(author, Author.class);
	// Check before if user exists
	final boolean authorExists = this.authorRepository.existsById(convertedAuthor.getId());
	if (!authorExists) {
	    throw new ResourceNotFoundException("Author with given id %s not exists", "id", author.getId());
	}
	return this.authorRepository.save(convertedAuthor);
    }
}
