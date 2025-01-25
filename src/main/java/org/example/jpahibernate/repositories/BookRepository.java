package org.example.jpahibernate.repositories;

import org.antlr.v4.runtime.misc.NotNull;
import org.example.jpahibernate.entities.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"libraries"})
    @Override
    Optional<Book> findById(@NonNull Long id);
}