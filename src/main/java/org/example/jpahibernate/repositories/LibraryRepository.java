package org.example.jpahibernate.repositories;

import org.example.jpahibernate.entities.Author;
import org.example.jpahibernate.entities.Library;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    @EntityGraph(attributePaths = {"books"})
    @Override
    Optional<Library> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"books"})
    @Override
    List<Library> findAll();
}