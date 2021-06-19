package com.github.darianbeluzzo.graphqlsimpleblog.repository;

import com.github.darianbeluzzo.graphqlsimpleblog.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
