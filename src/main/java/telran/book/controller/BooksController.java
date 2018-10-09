package telran.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.book.dao.IBookRepository;
import telran.book.dto.AuthorDto;
import telran.book.dto.PublisherDto;
import telran.book.entities.Author;
import telran.book.entities.Book;
import telran.book.entities.Publisher;

@RestController
public class BooksController {

	@Autowired
	IBookRepository bookRepository;

	@PostMapping("/book")
	public boolean addBook(@RequestBody Book book) {
		return bookRepository.addBook(book);
	}

	@GetMapping("/book/{isbn}")
	public Book getBookByIsbn(@PathVariable long isbn) {
		return bookRepository.getBookByIsbn(isbn);
	}

	@GetMapping("/book/author/{name}")
	public Iterable<Book> getBooksByAuthor(@PathVariable String name) {
		return bookRepository.getBooksByAuthor(name);
	}

	@GetMapping("publisher/{name}/books")
	public Iterable<Book> getBooksByPublisher(@PathVariable String name) {
		return bookRepository.getBooksByPublisher(name);
	}

	@GetMapping("/book/{isbn}/authors")
	public Iterable<Author> getBookAuthors(@PathVariable long isbn) {
		return bookRepository.getBookAuthors(isbn);
	}

	@GetMapping("/author/{name}/publishers")
	public Iterable<Publisher> getPublishersByAuthor(@PathVariable String name) {
		return bookRepository.getPublishersByAuthor(name);
	}

	@PutMapping("/book/{isbn}/{title}")
	public Book updateTitle(@PathVariable long isbn, @PathVariable String title) {
		return bookRepository.updateTitle(isbn, title);
	}

	@GetMapping("publisher/{name}")
	public PublisherDto getPublisher(@PathVariable String name) {
		return bookRepository.getPublisher(name);
	}

	@GetMapping("author/{name}")
	public AuthorDto getAuthor(@PathVariable String name) {
		return bookRepository.getAuthor(name);
	}

}
