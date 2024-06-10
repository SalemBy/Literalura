package salemby.com.github.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import salemby.com.github.literalura.model.Book;
import salemby.com.github.literalura.model.Language;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByLanguage(Language language);


}
