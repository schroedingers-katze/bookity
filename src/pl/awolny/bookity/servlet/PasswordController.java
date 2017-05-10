package pl.awolny.bookity.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.awolny.bookity.model.User;
import pl.awolny.bookity.service.UserService;


@WebServlet("/password")
public class PasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/password.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String oldPassword = request.getParameter("oldUsername");
        String newPassword = request.getParameter("newPassword");
        User ok = (User) request.getSession().getAttribute("user");
        String username = ok.getUsername();
        System.out.println("username" + username);

        UserService userService = new UserService();
        try{
            userService.updateUser(oldPassword, newPassword, username);
        } catch(Exception ex) {
        	ex.printStackTrace(System.out);
            request.getRequestDispatcher("WEB-INF/password.jsp").forward(request, response);
        	System.out.println("something went wrong while changing the password");
        }
        response.sendRedirect(request.getContextPath() + "/");
	}
}