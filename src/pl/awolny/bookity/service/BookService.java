package pl.awolny.bookity.service;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;

import pl.awolny.bookity.dao.DAOFactory;
import pl.awolny.bookity.dao.BookDAO;
import pl.awolny.bookity.model.Book;
import pl.awolny.bookity.model.User;
 
public class BookService {
    public void addBook(String title, String desc, String author, User user, String publisher, InputStream inputStream, int checking) {
    	System.out.println(checking);
        Book book = createBookObject(title, desc, author, user, publisher, inputStream, checking);
    	System.out.println("addBook" + book.getChecking());

        DAOFactory factory = DAOFactory.getDAOFactory();
        BookDAO bookDao = factory.getBookDAO();
        bookDao.create(book);
    }

     
    private Book createBookObject(String title, String desc, String author, User user, String publisher, InputStream inputStream, int checking) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(desc);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setInputStream(inputStream);
        book.setChecking(checking);
        User userCopy = new User(user);
        book.setUser(userCopy);
        book.setTimestamp(new Timestamp(new Date().getTime()));
        return book;
    }
     
    public Book getBookById(long bookId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BookDAO bookDao = factory.getBookDAO();
        Book book = bookDao.read(bookId);
        return book;
    }
     
    public boolean updateBook(Book book) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BookDAO bookDao = factory.getBookDAO();
        boolean result = bookDao.update(book);
        return result;
    }
     
    public List<Book> getAllBooks() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BookDAO bookDao = factory.getBookDAO();
        List<Book> books = bookDao.getAll();
        return books;
    }
     
    public List<Book> getAllBooks(Comparator<Book> comparator) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BookDAO bookDao = factory.getBookDAO();
        List<Book> books = bookDao.getAll();
        if(comparator != null && books != null) {
            books.sort(comparator);
        }
        return books;
    }


}