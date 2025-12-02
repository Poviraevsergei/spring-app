package by.tms.relation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

@Entity(name = "author")
@Data
@EqualsAndHashCode(exclude = {"books"})
@ToString(exclude = {"books"})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "author_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "author_name")
    private String authorName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "l_author_book", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Collection<Book> books;
}
