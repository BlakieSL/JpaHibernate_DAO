package org.example.jpahibernate.repositories;

import org.example.jpahibernate.entities.Author;
import org.example.jpahibernate.entities.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(attributePaths = {"books"})
    @Override
    Optional<Author> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"books"})
    @Override
    List<Author> findAll();
}