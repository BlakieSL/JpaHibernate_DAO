package org.example.jpahibernate.entities;

import jakarta.persistence.*;

@Entity
public class LibraryInfo {
    @Id
    private Long id;

    private String address;

    private String phone;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Library library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
