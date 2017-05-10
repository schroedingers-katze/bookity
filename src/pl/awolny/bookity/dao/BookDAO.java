package pl.awolny.bookity.dao;

import java.util.List;

import pl.awolny.bookity.model.Book;

public interface BookDAO extends GenericDAO<Book, Long>{	 
    List<Book> getAll();
}