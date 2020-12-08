package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Customer;
import model.Account;

public class AccountDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblAccount";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblAccount where id = ?";
	private static final String SELECT_BY_EMAIL_SQL_STATEMENT = "select * from tblAccount where email = ?";
	private static final String INSERT_SQL_STATEMENT = "insert into tblAccount(user_name, email, password, date_of_birth, gender, phone_number"
			+ "is_deleted, created_time, tblRole_id) values (?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_SQL_STATEMENT = "update tblAccount set customer_id = ?, user_name = ?, email = ?, password = ?, "
			+ "role = ?, is_deleted = ? where id = ?";
	private static final String DELETE_SQL_STATEMENT = "update tblAccount set is_deleted = 1 where id = ?";

	public ArrayList<Account> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Account> list = new ArrayList<>();
			RoleDAO roleDAO = new RoleDAO();
			
			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt(1));
				account.setUserName(rs.getString(2));
				account.setEmail(rs.getString(3));
				account.setPassword(rs.getString(4));
				account.setDateOfBirth(rs.getTimestamp(5));
				account.setGender(rs.getString(6));
				account.setPhoneNumber(rs.getString(7));
				account.setDeleted(rs.getBoolean(8));
				account.setCreatedTime(rs.getTimestamp(9));
				account.setRole(roleDAO.getById(rs.getInt(10)));
				list.add(account);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public Account getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			RoleDAO roleDAO = new RoleDAO();
			Account account = null;
			if (rs.next()) {
				account = new Account();
				account.setId(rs.getInt(1));
				account.setUserName(rs.getString(2));
				account.setEmail(rs.getString(3));
				account.setPassword(rs.getString(4));
				account.setDateOfBirth(rs.getTimestamp(5));
				account.setGender(rs.getString(6));
				account.setPhoneNumber(rs.getString(7));
				account.setDeleted(rs.getBoolean(8));
				account.setCreatedTime(rs.getTimestamp(9));
				account.setRole(roleDAO.getById(rs.getInt(10)));
			}
			statement.close();
			connection.close();
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Account getByEmail(String email) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_SQL_STATEMENT);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			RoleDAO roleDAO = new RoleDAO();
			Account account = null;
			if (rs.next()) {
				account = new Account();
				account.setId(rs.getInt(1));
				account.setUserName(rs.getString(2));
				account.setEmail(rs.getString(3));
				account.setPassword(rs.getString(4));
				account.setDateOfBirth(rs.getTimestamp(5));
				account.setGender(rs.getString(6));
				account.setPhoneNumber(rs.getString(7));
				account.setDeleted(rs.getBoolean(8));
				account.setCreatedTime(rs.getTimestamp(9));
				account.setRole(roleDAO.getById(rs.getInt(10)));
			}
			statement.close();
			connection.close();
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int insert(Account account) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);

			Timestamp dateOfBirth = account.getDateOfBirth() == null ? null : new Timestamp(account.getDateOfBirth().getTime());
            Timestamp createdTime = account.getCreatedTime() == null ? null : new Timestamp(account.getCreatedTime().getTime());
			

			statement.setString(1, account.getUserName());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getPassword());
			statement.setTimestamp(4, dateOfBirth);
			statement.setString(5, account.getPhoneNumber());
			statement.setBoolean(6, account.isDeleted());
			statement.setTimestamp(7, createdTime);
			statement.setInt(8, account.getRole().getId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				return 0;
			}
			int id = 0;
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				}
			}
			statement.close();
			connection.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

//	public int update(Account account) {
//		Connection connection = getConnection();
//		try {
//			PreparedStatement statement = connection.prepareStatement(UPDATE_SQL_STATEMENT);
//
//			Integer customerId = account.getCustomer() == null ? null : account.getCustomer().getId();
//
//			statement.setObject(1, customerId);
//			statement.setString(2, account.getUserName());
//			statement.setString(3, account.getEmail());
//			statement.setString(4, account.getPassword());
//			statement.setString(5, account.getRole());
//			statement.setObject(6, account.isDeleted());
//			statement.setObject(7, account.getId());
//
//			int affectedRows = statement.executeUpdate();
//			statement.close();
//			connection.close();
//			return affectedRows;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	public int delete(int id) {
//		Connection connection = getConnection();
//		try {
//			PreparedStatement statement = connection.prepareStatement(DELETE_SQL_STATEMENT);
//			statement.setInt(1, id);
//			int affectedRows = statement.executeUpdate();
//			statement.close();
//			connection.close();
//			return affectedRows;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
}
