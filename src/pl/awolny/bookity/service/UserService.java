package pl.awolny.bookity.service;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.jdbc.support.KeyHolder;

import pl.awolny.bookity.dao.DAOFactory;
import pl.awolny.bookity.dao.UserDAO;
import pl.awolny.bookity.model.User;

public class UserService {
    public void addUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        String md5Pass = encryptPassword(password);
        user.setPassword(md5Pass);
        user.setEmail(email);
        user.setIsActive(true);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        userDao.create(user);
    }
    
    public void updateUser(String oldPass, String newPass, String username) {
        User user = getUserByUsername(username);
        String omd5Pass = encryptPassword(oldPass);
        String nmd5Pass = encryptPassword(newPass);
        if(verifyPassword(user.getPassword(), omd5Pass)){
	        user.setPassword(nmd5Pass);
	
	        DAOFactory factory = DAOFactory.getDAOFactory();
	        UserDAO userDao = factory.getUserDAO();
	        userDao.update(user);
        }
    }

    private boolean verifyPassword(String currentPass, String givenPass) {
        if(currentPass.equals(givenPass)){
        	return true;
        } else{
        	return false; 
        }
    }
    
    
    private String encryptPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(password.getBytes());
        String md5Password = new BigInteger(1, digest.digest()).toString(16);
        return md5Password;
    }
    
    public User getUserById(long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.read(userId);
        return user;
    }
     
    public User getUserByUsername(String username) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.getUserByUsername(username);
        return user;
    }
}