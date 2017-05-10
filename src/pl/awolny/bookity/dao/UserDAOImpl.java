package pl.awolny.bookity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pl.awolny.bookity.dao.BookDAOImpl.BookRowMapper;
import pl.awolny.bookity.model.Book;
import pl.awolny.bookity.model.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.awolny.bookity.model.User;
import pl.awolny.bookity.util.ConnectionProvider;

public class UserDAOImpl implements UserDAO {

	private static final String CREATE_USER = "INSERT INTO user(username, email, password, is_active) VALUES(:username, :email, :password, :isActive);";
    private static final String READ_USER = "SELECT user_id, username, email, password, is_active FROM user WHERE user_id = :id";
    private static final String READ_USER_BY_USERNAME = "SELECT user_id, username, email, password, is_active FROM user WHERE username = :username";	 
    private static final String UPDATE_PASSWORD = "UPDATE user SET password=:password WHERE username = :username";	 

    private NamedParameterJdbcTemplate template;
	
	public UserDAOImpl(){
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}
	
    @Override
    public User create(User user) {
    	User resultUser = new User(user);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        int update = template.update(CREATE_USER, paramSource, holder);
        if(update > 0) {
            resultUser.setId(holder.getKey().longValue());
        	
            setPrivileges(resultUser);
            System.out.println("funkcja sendmail");
            sendEmail(user.getEmail(), user.getUsername());
        }
        
        return resultUser;
    }
	

     
    private void setPrivileges(User user){
    	final String userRoleQuery = "INSERT INTO user_role(username) VALUES(:username)";
    	SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        template.update(userRoleQuery, paramSource);
    }
    
    @Override
    public User read(Long primaryKey) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("id", primaryKey);
        resultUser = template.queryForObject(READ_USER, paramSource, new UserRowMapper());
        return resultUser;
    }
 
    @Override
    public boolean update(User user) {
    	SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        template.update(UPDATE_PASSWORD, paramSource);
    	
        return false;
    }
 
    @Override
    public boolean delete(Long key) {
        return false;
    }
 
    @Override
    public List<User> getAll() {
        return null;
    }
    @Override
    public User getUserByUsername(String username) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("username", username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, paramSource, new UserRowMapper());
        return resultUser;
    }
     
    private class UserRowMapper implements RowMapper<User> { 
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
         
    }
    
    public void sendEmail(String email, String username){
    	System.out.println();
    	   try{
    	        String host ="smtp.gmail.com" ;
    	        String user = "amvwolny@gmail.com";
    	        String pass = "xbrandenburger4%";
    	        String to = email;
    	        String from = "contact@bookity.pl";
    	        String subject = username + ", witaj w portalu bookity!";
    	        String messageText = "Stworzono u¿ytkownika " + username + ".";
    	    boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

  	        Transport transport = mailSession.getTransport("smtp");
   	        transport.connect(host, user, pass);
   	        transport.sendMessage(msg, msg.getAllRecipients());
   	        transport.close();
   	        System.out.println("Stworzono u¿ytkownika " + username + ".");
   	        System.out.println("message sent successfully");

    	   } catch(Exception ex){
    	           System.out.println(ex);
    	   }

    }
}