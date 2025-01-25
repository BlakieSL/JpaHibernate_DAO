package org.example.jpahibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "library", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private LibraryInfo libraryInfo;

    @ManyToMany(mappedBy = "libraries")
    private Set<Book> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibraryInfo getLibraryInfo() {
        return libraryInfo;
    }

    public void setLibraryInfo(LibraryInfo libraryInfo) {
        this.libraryInfo = libraryInfo;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
