package ru.ibalashov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "BOOK")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonIgnore
    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name = "book_word",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"),
            indexes = {
                    @Index(name = "idx_book_word_book_id", columnList = "book_id"),
                    @Index(name = "idx_book_word_word_id", columnList = "word_id")
            }
    )
    private Set<Word> words;

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
