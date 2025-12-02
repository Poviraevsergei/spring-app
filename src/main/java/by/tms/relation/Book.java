package by.tms.relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

@Entity(name = "books")
@Data
@ToString(exclude = {"authors","pages"})
@EqualsAndHashCode(exclude = {"authors","pages"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "book_name")
    private String bookName;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Collection<Page> pages;

    @JsonBackReference
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Collection<Author> authors;

}
