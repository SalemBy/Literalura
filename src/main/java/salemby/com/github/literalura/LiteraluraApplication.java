package salemby.com.github.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import salemby.com.github.literalura.commandLineView.Menu;
import salemby.com.github.literalura.repository.AuthorRepository;
import salemby.com.github.literalura.repository.BooksRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BooksRepository booksRepository;

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(booksRepository, authorRepository);
		menu.createMenu();
	}
}
