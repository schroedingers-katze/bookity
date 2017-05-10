package pl.awolny.bookity.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
 
import pl.awolny.bookity.model.Book;
import pl.awolny.bookity.model.User;
import pl.awolny.bookity.util.ConnectionProvider;
 
public class BookDAOImpl implements BookDAO {

	private static final String SET_UTF = "SET NAMES utf8_polish_ci; ";

	private static final String SET_UTF_AGAIN = "SET CHARACTER SET utf8_polish_ci";
    private static final String CREATE_BOOK = 
      "INSERT INTO book(title, description, author, user_id, date, publisher, photo, checking) "
      + "VALUES(:title, :description, :author, :user_id, :date, :publisher, :photo, :checking);";
    private static final String READ_ALL_BOOKS = 
      "SELECT user.user_id, username, email, is_active, password, book_id, title, description, author, publisher, date, checking "
      + "FROM book LEFT JOIN user ON book.user_id=user.user_id;";
    private static final String READ_BOOK = 
        "SELECT user.user_id, username, email, is_active, password, book_id, title, description, author, date, publisher, checking "
        + "FROM book LEFT JOIN user ON book.user_id=user.user_id WHERE book_id=:book_id;";
    private static final String UPDATE_BOOK = 
        "UPDATE book SET title=:title, description=:description, author=:author, user_id=:user_id, date=:date, publisher=:publisher "
        + "WHERE book_id=:book_id;";
    
 
    private NamedParameterJdbcTemplate template;
     
    public BookDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
 
    @Override
    public Book create(Book book) {
        Book resultBook = new Book(book);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("title", book.getTitle());
        paramMap.put("description", book.getDescription());
        paramMap.put("author", book.getAuthor());
        paramMap.put("user_id", book.getUser().getId());
        paramMap.put("date", book.getTimestamp());
        paramMap.put("publisher", book.getPublisher());
        paramMap.put("photo", book.getInputStream());
        paramMap.put("checking", book.getChecking());
        MapSqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        template.update(SET_UTF, paramSource);
        template.update(SET_UTF_AGAIN, paramSource);
        int update = template.update(CREATE_BOOK, paramSource, holder);
        if(update > 0) {
            resultBook.setId((Long)holder.getKey());
        }
        return resultBook;
    }
 
    @Override
    public Book read(Long primaryKey) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource("book_id", primaryKey);
        Book book = template.queryForObject(READ_BOOK, paramSource, new BookRowMapper());
        return book;
    }
 
    @Override
    public boolean update(Book book) {
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("book_id", book.getId());
        paramMap.put("title", book.getTitle());
        paramMap.put("description", book.getDescription());
        paramMap.put("author", book.getAuthor());
        paramMap.put("publisher", book.getPublisher());
        paramMap.put("user_id", book.getUser().getId());
        paramMap.put("date", book.getTimestamp());

        MapSqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_BOOK, paramSource);

        if(update > 0) {
            result = true;
        }
        return result;
    }
 
    @Override
    public boolean delete(Long key) {
        return false;
    }
 
    @Override
    public List<Book> getAll() {
        List<Book> books = template.query(READ_ALL_BOOKS, new BookRowMapper());
        return books;
    }
     
    public class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int row) throws SQLException {
            Book book = new Book();
            book.setId(resultSet.getLong("book_id"));
            book.setTitle(resultSet.getString("title"));
            book.setDescription(resultSet.getString("description"));
            book.setAuthor(resultSet.getString("author"));
            book.setPublisher(resultSet.getString("publisher"));
            book.setTimestamp(resultSet.getTimestamp("date"));
            book.setChecking(resultSet.getInt("checking"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            book.setUser(user);
            return book;
        }
    }
}