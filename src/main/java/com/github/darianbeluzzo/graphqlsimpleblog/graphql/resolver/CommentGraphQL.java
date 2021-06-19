package com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver;

import com.github.darianbeluzzo.graphqlsimpleblog.domain.Comment;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.model.CommentInput;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.CommentRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CommentRepository commentRepository;

    @Autowired
    private ModelMapper mapper;

    public CommentGraphQL(final CommentRepository commentRepository) {
	this.commentRepository = commentRepository;
    }

    public Comment createComment(final CommentInput comment) {
	final Comment convertedComment = mapper.map(comment, Comment.class);
	return this.commentRepository.save(convertedComment);
    }

    public boolean deleteComment(final Long id) {
	this.commentRepository.deleteById(id);
	return true;
    }

    public Optional<Comment> getComment(final Long id) {
	return this.commentRepository.findById(id);
    }

    public List<Comment> listComments() {
	return this.commentRepository.findAll();
    }

    public Comment updateComment(final CommentInput comment) {
	final Comment convertedComment = mapper.map(comment, Comment.class);
	return this.commentRepository.save(convertedComment);
    }

}
