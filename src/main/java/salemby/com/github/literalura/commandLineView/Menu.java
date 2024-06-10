package salemby.com.github.literalura.commandLineView;

import com.fasterxml.jackson.databind.JsonNode;
import salemby.com.github.literalura.model.Author;
import salemby.com.github.literalura.model.Book;
import salemby.com.github.literalura.model.BookData;
import salemby.com.github.literalura.model.Language;
import salemby.com.github.literalura.repository.AuthorRepository;
import salemby.com.github.literalura.repository.BooksRepository;
import salemby.com.github.literalura.services.ApiService;
import salemby.com.github.literalura.services.ConvertData;


import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiService apiService = new ApiService();
    private final ConvertData convertData = new ConvertData();
    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;

    public Menu(BooksRepository booksRepository, AuthorRepository authorRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
    }

    public void createMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("""
                    1. Search Books.
                    2. Show All Books.
                    3. Show All Authors.
                    4. Show Authors by Year alive.
                    5. Show books by language.
                    0. Exit.
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBookInApi();
                    break;

                case 2:
                    findAllBooks();
                    break;

                case 3:
                    findAllAuthors();
                    break;

                case 4:
                    findAuthorByYearAlive();
                    break;

                case 5:
                    findBooksByLang();
                    break;
                case 0:
                    System.out.println("Bye, bye");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    private void searchBookInApi() {
        BookData bookData = getBookData();
        if (bookData != null) {
            Book book = new Book(bookData);
            booksRepository.save(book);
            System.out.println("Book saved: " + book);
        } else {
            System.out.println("No book data to save");
        }
    }

    private BookData getBookData() {
        try {
            System.out.println("Enter the book title: ");
            var bookTitle = scanner.nextLine();

            String ADDRESS = "https://gutendex.com/books/?search=";
            String URL = ADDRESS + bookTitle.replace(" ", "%20");
            String json = apiService.apiConsume(URL);

            JsonNode rootNode = convertData.getObjectMapper().readTree(json);
            JsonNode resultsNode = rootNode.path("results");

            if (resultsNode.isArray() && !resultsNode.isEmpty()) {
                JsonNode firstBookNode = resultsNode.get(0);
                return convertData.getObjectMapper().treeToValue(firstBookNode, BookData.class);
            } else {
                System.out.println("No book found");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void findAllBooks() {
        List<Book> allBooks = booksRepository.findAll();
        for (Book e : allBooks) {
            System.out.println("Book: " + e.getTitle());
        }
    }

    private void findAllAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        allAuthors.forEach(a -> System.out.println(a.getName() + " " + a.getBirthYear() + " " + a.getDeathYear() + " "));
    }

    private void findAuthorByYearAlive() {
        System.out.println("What year are you searching? ");
        var year = scanner.nextInt();
        scanner.nextLine();

        List<Author> authorFound = authorRepository.findAuthorsAliveInYear(year);
        authorFound.forEach(a -> System.out.println(a.getName() + " " + a.getBirthYear() + " " + a.getDeathYear() + " "));
    }

    private void findBooksByLang() {
        System.out.println("Choose a language (pt - en)");
        var langInput = scanner.nextLine();
        Language lang = Language.fromString(langInput);

        List<Book> foundBooks = booksRepository.findBooksByLanguage(lang);

        for (Book book : foundBooks) {
            System.out.println(book.getTitle());
        }
    }
}


