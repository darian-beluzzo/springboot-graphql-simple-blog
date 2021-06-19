package com.github.darianbeluzzo.graphqlsimpleblog.repository;

import com.github.darianbeluzzo.graphqlsimpleblog.SimpleBlogApplication;
import com.github.darianbeluzzo.graphqlsimpleblog.domain.Author;
import com.github.darianbeluzzo.graphqlsimpleblog.domain.Post;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = SimpleBlogApplication.class)
public class AuthorRepositoryTest {

    private final List<Author> objectsToDelete = new ArrayList<>();

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostRepository postRepository;

    public static Author createRamdomAuthor() {
	final Author author = new Author();
	author.setName(RandomStringUtils.random(10));
	author.setUsername(RandomStringUtils.random(10));
	author.setEmail(RandomStringUtils.random(10) + "@email.com");
	return author;
    }

    public static Author createRamdomAuthorWithId() {
	final Author author = createRamdomAuthor();
	author.setId(RandomUtils.nextLong());
	return author;
    }

    public static Post createRandomPost() {
	final Post post = new Post();
	post.setTitle(RandomStringUtils.random(10));
	post.setBody(RandomStringUtils.random(10));
	return post;
    }

    public static Post createRandomPostWithId() {
	final Post post = createRandomPost();
	post.setId(RandomUtils.nextLong());
	return post;
    }

    @AfterEach
    public void after() {
	try {
	    for (Author author : objectsToDelete) {
		authorRepository.deleteById(author.getId());
	    }
	} finally {
	    objectsToDelete.clear();
	}
    }

    @Test
    public void testCreateAuthor() {
	// Create
	Author newAuthor = createRamdomAuthor();
	Author savedAuthor = authorRepository.save(newAuthor);
	registerToDelete(savedAuthor);
	// Check
	assertThat(savedAuthor.getId()).isNotNull();
	assertThat(savedAuthor.getName()).isEqualTo(newAuthor.getName());
	assertThat(savedAuthor.getUsername()).isEqualTo(newAuthor.getUsername());
	assertThat(savedAuthor.getEmail()).isEqualTo(newAuthor.getEmail());
    }

    @Test
    public void testDeleteAuthorById() {
	// Create
	final Author newAuthor = createRamdomAuthor();
	final Author savedAuthor = authorRepository.save(newAuthor);
	assertThat(savedAuthor.getId()).isNotNull();
	// Delete
	authorRepository.deleteById(savedAuthor.getId());
	final Optional<Author> loadedAuthorOptional = authorRepository.findById(savedAuthor.getId());
	// Check
	assertThat(loadedAuthorOptional).isEmpty();
    }

    @Test
    public void testDeleteAuthorByIdThatNotExists() {
	assertThrows(EmptyResultDataAccessException.class, () -> {
	    authorRepository.deleteById(-1L);
	});
    }

    @Test
    public void testFindAuthorById() {
	// Create
	Author newAuthor = createRamdomAuthor();
	Author savedAuthor = authorRepository.save(newAuthor);
	assertThat(savedAuthor).isNotNull();
	registerToDelete(savedAuthor);
	// Find
	Optional<Author> loadedAuthorOptional = authorRepository.findById(savedAuthor.getId());
	// Check
	assertThat(loadedAuthorOptional).isNotNull();
	final Author loadedAuthor = loadedAuthorOptional.orElseThrow();
	assertThat(loadedAuthor.getId()).isEqualTo(newAuthor.getId());
	assertThat(loadedAuthor.getName()).isEqualTo(newAuthor.getName());
	assertThat(loadedAuthor.getUsername()).isEqualTo(newAuthor.getUsername());
	assertThat(loadedAuthor.getEmail()).isEqualTo(newAuthor.getEmail());
    }

    @Test
    public void testUpdateAuthor() {
	// Create
	Author newAuthor = createRamdomAuthor();
	Author savedAuthor = authorRepository.save(newAuthor);
	registerToDelete(savedAuthor);
	assertThat(savedAuthor).isNotNull();
	// Update
	newAuthor = createRamdomAuthor();
	newAuthor.setId(savedAuthor.getId());
	Author updatedAuthor = authorRepository.save(newAuthor);
	// Check
	assertThat(updatedAuthor.getId()).isEqualTo(newAuthor.getId());
	assertThat(updatedAuthor.getName()).isEqualTo(newAuthor.getName());
	assertThat(updatedAuthor.getUsername()).isEqualTo(newAuthor.getUsername());
	assertThat(updatedAuthor.getEmail()).isEqualTo(newAuthor.getEmail());
    }

    @Test
    public void testfindAuthorAndLoadPosts() {
	// Create
	Author savedAuthor = authorRepository.save(createRamdomAuthor());
	final Post randomPost = createRandomPost();
	randomPost.setAuthorId(savedAuthor.getId());
	Post savedPost = postRepository.save(randomPost);
	assertThat(savedPost).isNotNull();

	List<Author> loaderAuthors = authorRepository.findAll();
	// Check
	//assertThat(loaderAuthors).hasSameElementsAs(Arrays.asList(savedAuthor1, savedAuthor2));
	System.out.println("");
    }

    @Test
    public void testfindAuthors() {
	// Create
	Author savedAuthor1 = authorRepository.save(createRamdomAuthor());
	Author savedAuthor2 = authorRepository.save(createRamdomAuthor());
	registerToDelete(savedAuthor1, savedAuthor2);
	assertThat(savedAuthor1).isNotNull();
	assertThat(savedAuthor2).isNotNull();
	List<Author> loaderAuthors = authorRepository.findAll();
	// Check
	assertThat(loaderAuthors).hasSameElementsAs(Arrays.asList(savedAuthor1, savedAuthor2));
    }

    private void registerToDelete(final Author... authors) {
	objectsToDelete.addAll(Arrays.asList(authors));
    }
}
