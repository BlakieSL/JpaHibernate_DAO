package org.example.jpahibernate;

import org.example.jpahibernate.entities.Author;
import org.example.jpahibernate.entities.Book;
import org.example.jpahibernate.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(
        scripts = {
                "classpath:/schema/drop-schema.sql",
                "classpath:/schema/create-schema.sql"
        }
)
@Testcontainers
@SpringBootTest
public class BookRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryTest.class);
    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findByIdWithAssociations - Should return Book with Libraries")
    @Test
    void testFindByIdWithAssociations() {
        var result = bookRepository.findById(1L);

        assertTrue(result.isPresent());

        var book = result.get();
        assertEquals(1L, book.getId());
        assertEquals(1L, book.getAuthor().getId());
        assertEquals("Book One by John", book.getTitle());
        assertEquals(LocalDate.of(2023, 1, 15), book.getReleaseDate());

        var libraries = book.getLibraries();
        assertEquals(1, libraries.size());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findByIdWithAssociations - Should return Optional.empty()")
    @Test
    void testFindByIdNotFound() {
        var result = bookRepository.findById(999L);

        assertTrue(result.isEmpty());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST findAll - Should return all Books")
    @Test
    void testFindAll() {
        var result = bookRepository.findAll();
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());

        assertTrue(result.stream().anyMatch(book ->
                book.getId() == 1L &&
                        book.getAuthor().getId() == 1L &&
                        "Book One by John".equals(book.getTitle()) &&
                        LocalDate.of(2023, 1, 15).equals(book.getReleaseDate())
        ));

        assertTrue(result.stream().anyMatch(book ->
                book.getId() == 2L &&
                        book.getAuthor().getId() == 1L &&
                        "Book Two by John".equals(book.getTitle()) &&
                        LocalDate.of(2023, 3, 10).equals(book.getReleaseDate())
        ));

        assertTrue(result.stream().anyMatch(book ->
                book.getId() == 3L &&
                        book.getAuthor().getId() == 2L &&
                        "Jane's Journey".equals(book.getTitle()) &&
                        LocalDate.of(2022, 5, 22).equals(book.getReleaseDate())
        ));

        assertTrue(result.stream().anyMatch(book ->
                book.getId() == 4L &&
                        book.getAuthor().getId() == 3L &&
                        "Emily's Adventures".equals(book.getTitle()) &&
                        LocalDate.of(2021, 12, 5).equals(book.getReleaseDate())
        ));
    }

    @DisplayName("TEST findAll - Should return empty Set")
    @Test
    void testFindAllEmpty() {
        var result = bookRepository.findAll();
        assertTrue(result.isEmpty());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST create - Should create and return Book")
    @Test
    void testCreate() {
        var author = new Author(1L);
        var newBook = new Book(null, "New Book", LocalDate.of(2023, 10, 10), author);
        var createdBook = bookRepository.save(newBook);

        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertEquals(1L, createdBook.getAuthor().getId());
        assertEquals("New Book", createdBook.getTitle());
        assertEquals(LocalDate.of(2023, 10, 10), createdBook.getReleaseDate());
    }

    @DisplayName("TEST create - Should fail for missing fields")
    @Test
    void testCreateWithMissingFields() {
        var incompleteBook = new Book(null, "Incomplete Book", null, null);

        assertThrows(Exception.class, () -> bookRepository.save(incompleteBook));
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST update - Should update and return Book")
    @Test
    void testUpdate() {
        var author = new Author(1L);
        var existingBook = new Book(1L, "Updated Title", LocalDate.of(2023, 1, 15), author);
        var updatedBook = bookRepository.save(existingBook);

        assertNotNull(updatedBook);
        assertEquals(1L, updatedBook.getId());
        assertEquals(1L, updatedBook.getAuthor().getId());
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals(LocalDate.of(2023, 1, 15), updatedBook.getReleaseDate());
    }

    @SqlSetupAuthorBook
    @DisplayName("TEST delete - Should delete and return true")
    @Test
    void testDelete() {
        var entityToDelete = bookRepository.findById(1L).get();
        bookRepository.delete(entityToDelete);

        Optional<Book> deletedBook = bookRepository.findById(1L);
        assertTrue(deletedBook.isEmpty());
    }
}