package org.example.jpahibernate;


import org.example.jpahibernate.entities.Author;
import org.example.jpahibernate.repositories.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the AuthorRepository class using same database as specified in
 * production properties and static SQL scripts.
 */

@SpringBootTest
public class AuthorRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryTest.class);

    private AuthorRepository authorRepository;

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findByIdWithAssociations - Should return Author")
    @Test
    void testFindByIdWithAssociations() {
        var result = authorRepository.findById(1L);
        assertTrue(result.isPresent());

        var author = result.get();
        assertEquals(1L, author.getId());
        assertEquals("John", author.getFirstName());
        assertEquals("Doe", author.getLastName());
        assertEquals(2, author.getBooks().size());
        assertTrue(author.getBooks().stream().anyMatch(book -> book.getId() == 1L && "Book One by John".equals(book.getTitle())));
        assertTrue(author.getBooks().stream().anyMatch(book -> book.getId() == 2L && "Book Two by John".equals(book.getTitle())));
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findByIdWithAssociations - Should return Optional.empty()")
    @Test
    void testFindByIdWithAssociationsNotFound() {
        var result = authorRepository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findAll - Should return all Authors")
    @Test
    void testFindAll() {
        var result = authorRepository.findAll();
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());

        assertTrue(result.stream().anyMatch(author ->
                author.getId() == 1L &&
                        "John".equals(author.getFirstName()) &&
                        "Doe".equals(author.getLastName()) &&
                        author.getBooks().size() == 2 &&
                        author.getBooks().stream().anyMatch(book -> book.getId() == 1L && "Book One by John".equals(book.getTitle())) &&
                        author.getBooks().stream().anyMatch(book -> book.getId() == 2L && "Book Two by John".equals(book.getTitle()))
        ));

        assertTrue(result.stream().anyMatch(author ->
                author.getId() == 2L &&
                        "Jane".equals(author.getFirstName()) &&
                        "Smith".equals(author.getLastName()) &&
                        author.getBooks().size() == 1 &&
                        author.getBooks().stream().anyMatch(book -> book.getId() == 3L && "Jane's Journey".equals(book.getTitle()))
        ));

        assertTrue(result.stream().anyMatch(author ->
                author.getId() == 3L &&
                        "Emily".equals(author.getFirstName()) &&
                        "Johnson".equals(author.getLastName()) &&
                        author.getBooks().size() == 1 &&
                        author.getBooks().stream().anyMatch(book -> book.getId() == 4L && "Emily's Adventures".equals(book.getTitle()))
        ));
    }

    @DisplayName("TEST findAll - Should return empty Set")
    @Test
    void testFindAllEmpty() {
        var result = authorRepository.findAll();
        assertTrue(result.isEmpty());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST create - Should create and return Author")
    @Test
    void testCreate() {
        var newAuthor = new Author(null, "Alice", "Walker", Set.of());
        var createdAuthor = authorRepository.save(newAuthor);

        assertNotNull(createdAuthor.getId());
        assertEquals("Alice", createdAuthor.getFirstName());
        assertEquals("Walker", createdAuthor.getLastName());
        assertTrue(createdAuthor.getBooks().isEmpty());
    }

    @DisplayName("TEST create - Should fail for missing fields")
    @Test
    void testCreateWithMissingFields() {
        var incompleteAuthor = new Author(null, null, "Walker", Set.of());

        assertThrows(Exception.class, () -> authorRepository.save(incompleteAuthor));
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST update - Should update and return Author")
    @Test
    void testUpdate() {
        var existingAuthor = new Author(1L, "UpdatedFirstName", "UpdatedLastName", Set.of());
        var updatedAuthor = authorRepository.save(existingAuthor);

        assertEquals(1L, updatedAuthor.getId());
        assertEquals("UpdatedFirstName", updatedAuthor.getFirstName());
        assertEquals("UpdatedLastName", updatedAuthor.getLastName());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST delete - Should delete and return true")
    @Test
    void testDelete() {
        var entityToDelete = authorRepository.findById(1L).get();
        authorRepository.delete(entityToDelete);

        Optional<Author> deletedAuthor = authorRepository.findById(1L);
        assertTrue(deletedAuthor.isEmpty());
    }
}
