package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Account;
import model.Address;
import model.Customer;

public class CustomerDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblCustomer";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblCustomer where tblAccount_id = ?";
	
	public ArrayList<Customer> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Customer> list = new ArrayList<>();
			AccountDAO accountDAO = new AccountDAO();
			
			while (rs.next()) {
				Address address = new Address();
				address.setHouseNumber(rs.getString(3));
				address.setStreet(rs.getString(4));
				address.setProvince(rs.getString(5));
				address.setDistrict(rs.getString(6));
				address.setCity(rs.getString(7));
				address.setCountry(rs.getString(8));
				
				int id = rs.getInt(1);
				
				Account account = accountDAO.getById(id);
				
				Customer customer = new Customer();
				
				customer.setId(id);
				customer.setCollectedPoint(rs.getInt(2));
				customer.setAddress(address);
				
				customer.setUserName(account.getUserName());
				customer.setEmail(account.getEmail());
				customer.setPassword(account.getPassword());
				customer.setDateOfBirth(account.getDateOfBirth());
				customer.setGender(account.getGender());
				customer.setPhoneNumber(account.getPhoneNumber());
				customer.setDeleted(account.isDeleted());
				customer.setCreatedTime(account.getCreatedTime());
				customer.setRole(account.getRole());
				
				list.add(customer);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Customer getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			AccountDAO accountDAO = new AccountDAO();
			Customer customer = null;
			if (rs.next()) {
				customer = new Customer();
				
				Address address = new Address();
				address.setHouseNumber(rs.getString(3));
				address.setStreet(rs.getString(4));
				address.setProvince(rs.getString(5));
				address.setDistrict(rs.getString(6));
				address.setCity(rs.getString(7));
				address.setCountry(rs.getString(8));
								
				Account account = accountDAO.getById(id);
								
				customer.setId(id);
				customer.setCollectedPoint(rs.getInt(2));
				customer.setAddress(address);
				
				customer.setUserName(account.getUserName());
				customer.setEmail(account.getEmail());
				customer.setPassword(account.getPassword());
				customer.setDateOfBirth(account.getDateOfBirth());
				customer.setGender(account.getGender());
				customer.setPhoneNumber(account.getPhoneNumber());
				customer.setDeleted(account.isDeleted());
				customer.setCreatedTime(account.getCreatedTime());
				customer.setRole(account.getRole());
			}
			statement.close();
			connection.close();
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public int insert(Customer customer) {
//		// do something
//	}
//	
//	public int update(Customer customer) {
//		// do something
//	}
//	
//	public int delete(int id) {
//		// do something
//	}
}
