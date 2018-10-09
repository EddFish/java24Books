package telran.book.dao;

import telran.book.dto.AuthorDto;
import telran.book.dto.PublisherDto;
import telran.book.entities.Author;
import telran.book.entities.Book;
import telran.book.entities.Publisher;

public interface IBookRepository {
	
	boolean addBook(Book book);
	
	boolean removeBook(long isbn);
	
	Book getBookByIsbn(long isbn);
	
	Iterable<Book> getBooksByAuthor(String authorName);
	
	Iterable<Book> getBooksByPublisher(String publisherName);
	
	Iterable<Author> getBookAuthors(long isbn); 
	
	Iterable<Publisher> getPublishersByAuthor(String authorName);
	
	Book updateTitle(long isbn, String title);
	
	PublisherDto getPublisher(String name);
	
	AuthorDto getAuthor(String name);

}
