package salemby.com.github.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false)
        private Integer idBook;

        @Column(nullable = false, length = 510)
        private String title;

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Author> authors = new ArrayList<>();

        @Enumerated(EnumType.STRING)
        private Language language;

        public Book() {}

        public Book(BookData bookData) {
                this.idBook = bookData.id();
                this.title = bookData.title();
                this.language = Language.fromString(bookData.languages().get(0).trim());
                this.authors = bookData.authors().stream().map(Author::new).toList();
        }

        public String getTitle() {
                return title;
        }

        public List<Author> getAuthors() {
                return authors;
        }

        public Language getLanguage() {
                return language;
        }

        @Override
        public String toString() {
                return "Book{" +
                        "title='" + title + '\'' +
                        ", authors=" + authors.toString() +
                        ", language=" + language +
                        '}';
        }
}
