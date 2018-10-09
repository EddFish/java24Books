package telran.book.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.book.dto.AuthorDto;
import telran.book.dto.PublisherDto;
import telran.book.entities.Author;
import telran.book.entities.Book;
import telran.book.entities.Publisher;

@Repository
public class BookRepositoryImpl implements IBookRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public boolean addBook(Book book) {
		// step 1
		if (em.find(Book.class, book.getIsbn()) != null) {
			return false;
		}
		// step 2
		Publisher publisher = book.getPublisher();
		if (em.find(Publisher.class, publisher.getPublisherName()) == null) {
			em.persist(publisher);
		}
		// step 3
		Set<Author> authors = book.getAuthors();
		for (Author author : authors) {
			if (em.find(Author.class, author.getName()) == null) {
				em.persist(author);
			}
		}
		// step 4
		em.persist(book);
		return true;
	}

	@Override
	@Transactional
	public boolean removeBook(long isbn) {
		Book book = em.find(Book.class, isbn);
		if (book == null) {
			return false;
		}

		em.remove(book);

		return true;
	}

	@Override
	public Iterable<Book> getBooksByAuthor(String authorName) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.name=?1", Book.class);
		query.setParameter(1, authorName);
		return query.getResultList();
	}

	@Override
	public Iterable<Book> getBooksByPublisher(String publisherName) {
		Publisher publisher = em.find(Publisher.class, publisherName);
		if (publisher == null) {
			return null;
		}
		return publisher.getBooks();
	}

	@Override
	public Iterable<Author> getBookAuthors(long isbn) {
		Book book = em.find(Book.class, isbn);
		if (book == null) {
			return null;
		}
		return book.getAuthors();
	}

	@Override
	public Iterable<Publisher> getPublishersByAuthor(String authorName) {
		TypedQuery<Publisher> query = em.createQuery(
				"SELECT DISTINCT p FROM Book b " + "JOIN b.authors a JOIN b.publisher p WHERE a.name=?1",
				Publisher.class);
		query.setParameter(1, authorName);
		return query.getResultList();
	}

	@Override
	public Book getBookByIsbn(long isbn) {
		return em.find(Book.class, isbn);
	}

	@Override
	@Transactional
	public Book updateTitle(long isbn, String title) {
		Book book = em.find(Book.class, isbn);
		if (book == null) {
			return null;
		}
		book.setTitle(title);
		return book;
	}

	@Override
	public PublisherDto getPublisher(String name) {
		Publisher publisher = em.find(Publisher.class, name);
		if (publisher == null) {
			return null;
		}
		PublisherDto publisherDto = new PublisherDto();
		publisherDto.setPublisherName(publisher.getPublisherName());
		Set<Book> books = publisher.getBooks();
		List<String> bookTitles = new ArrayList<>();
		for (Book book : books) {
			bookTitles.add(book.getTitle());
		}
		publisherDto.setBooks(bookTitles);
		return publisherDto;
	}

	@Override
	public AuthorDto getAuthor(String name) {
		Author author = em.find(Author.class, name);
		if (author == null) {
			return null;
		}
		AuthorDto.AuthorDtoBuilder builder = AuthorDto.builder().name(name);
		Set<Book> books = author.getBooks();
		for (Book book : books) {
			builder = builder.book(book.getTitle());
		}
		return builder.build();
	}

}
