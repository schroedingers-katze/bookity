package pl.awolny.bookity.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.*; 

@MultipartConfig
@WebServlet("/img")
public class ImgController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String connectionURL = "jdbc:mysql://mysql41851-bookity.unicloud.pl/bookity";
		String id = request.getParameter("book_id");

		/*declare a resultSet that works as a table resulted by execute a specified 
		sql query. */
		ResultSet rs = null;
		// Declare statement.
		PreparedStatement psmnt = null;
		// declare InputStream object to store binary stream of given image.
		InputStream sImage;
		try {
			//�Load JDBC driver "com.mysql.jdbc.Driver"
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			/* Create a connection by using getConnection() method that takes 
			parameters of string type connection url, user name and password to 
			connect to database. */
			Connection connection = DriverManager.getConnection(connectionURL, "administrator", "administrator");
			/* prepareStatement() is used for create statement object that is 
			used for sending sql statements to the specified database. */
			psmnt = connection.prepareStatement("SELECT photo FROM book WHERE book_id = ?");
			psmnt.setString(1, id); 
			rs = psmnt.executeQuery();
			if(rs.next()) {
				byte[] bytearray = new byte[1048576];
				int size=0;
				sImage = rs.getBinaryStream(1);
				System.out.println(sImage);
				response.reset();
				response.setContentType("image/jpeg");
				while((size=sImage.read(bytearray))!= -1 ){
					response.getOutputStream().write(bytearray,0,size);
				}
			}
		} catch(Exception ex){
			System.out.println("error :"+ex);
		}
		finally {
		// close all the connections.
		//rs.close();
		//psmnt.close();
		//connection.close();
		}
	}
}