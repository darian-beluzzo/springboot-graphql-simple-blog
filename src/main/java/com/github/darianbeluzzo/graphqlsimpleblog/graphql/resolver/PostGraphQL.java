package com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver;

import com.github.darianbeluzzo.graphqlsimpleblog.domain.Post;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.exception.ResourceNotFoundException;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.model.PostInput;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    public PostGraphQL(final PostRepository postRepository) {
	this.postRepository = postRepository;
    }

    public Post createPost(final PostInput post) {
	final Post convertedPost = mapper.map(post, Post.class);
	return this.postRepository.save(convertedPost);
    }

    public boolean deletePost(final Long id) {
	this.postRepository.deleteById(id);
	return true;
    }

    public Post getPost(final Long id) {
	return this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with given id %s not exists", "id", id));
    }

    public List<Post> listPosts() {
	return this.postRepository.findAll();
    }

    public Post updatePost(final PostInput post) {
	final Post convertedPost = mapper.map(post, Post.class);
	return this.postRepository.save(convertedPost);
    }
}
