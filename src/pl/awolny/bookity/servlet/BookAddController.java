package pl.awolny.bookity.servlet;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pl.awolny.bookity.model.User;
import pl.awolny.bookity.service.BookService;
 
@WebServlet("/add")
@MultipartConfig(maxFileSize = 16177215)
public class BookAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getUserPrincipal() != null) {
            request.getRequestDispatcher("/WEB-INF/new.jsp").forward(request, response);
        } else {
            response.sendError(403);
        }
    } 
    @Override//(name, desc, author, user, publisher, year)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("inputTitle");
        String description = request.getParameter("inputDescription");
        String author = request.getParameter("inputAuthor");
        String publisher = request.getParameter("inputPublisher");
        int checking = 0;
        
        InputStream inputStream = null; 
        
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("photo");
        if (filePart != null && filePart.getSize() > 0) {
        	checking = 1;
            // prints out debugging info
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
        
        
        User authenticatedUser = (User) request.getSession().getAttribute("user");
        if(request.getUserPrincipal() != null) {
            BookService bookService = new BookService();
            bookService.addBook(title, description, author, authenticatedUser, publisher, inputStream, checking);
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            response.sendError(403);
        }
    }
}