package pl.awolny.bookity.dao;

public class MysqlDAOFactory extends DAOFactory {

	@Override
	public BookDAO getBookDAO() {
		// TODO Auto-generated method stub
		return new BookDAOImpl();
	}

	@Override
	public UserDAO getUserDAO() {
		// TODO Auto-generated method stub
		return new UserDAOImpl();
	}

}
