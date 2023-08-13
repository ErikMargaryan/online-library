package com.library.persistence.entity;

import com.library.persistence.entity.joinEntity.LibraryBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "libraries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "website")
    private URL website;
    @OneToMany(mappedBy = "library", cascade = CascadeType.PERSIST)
    private List<LibraryBook> libraryBooks = new ArrayList<>();
}
