package pl.awolny.bookity.dao;

import java.util.List;
import pl.awolny.bookity.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
	 
    List<User> getAll();
    User getUserByUsername(String username);
     
}