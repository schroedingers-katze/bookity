package pl.awolny.bookity.servlet;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import pl.awolny.bookity.model.Book;
import pl.awolny.bookity.service.BookService;
 
@WebServlet
public class HomeController extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveBooksInRequest(request);
        
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
 
    private void saveBooksInRequest(HttpServletRequest request) {
        BookService bookService = new BookService();
        List<Book> allBooks = bookService.getAllBooks();
        request.setAttribute("books", allBooks);
    }
}