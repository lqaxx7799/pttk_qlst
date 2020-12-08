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
import model.Employee;

public class EmployeeDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblEmployee";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblEmployee where tblAccount_id = ?";
	
	public ArrayList<Employee> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Employee> list = new ArrayList<>();
			AccountDAO accountDAO = new AccountDAO();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				
				Account account = accountDAO.getById(id);
				
				Employee employee = new Employee();
				
				employee.setId(id);
				employee.setSalary(rs.getFloat(2));
				employee.setWorking(rs.getBoolean(3));
				
				employee.setUserName(account.getUserName());
				employee.setEmail(account.getEmail());
				employee.setPassword(account.getPassword());
				employee.setDateOfBirth(account.getDateOfBirth());
				employee.setGender(account.getGender());
				employee.setPhoneNumber(account.getPhoneNumber());
				employee.setDeleted(account.isDeleted());
				employee.setCreatedTime(account.getCreatedTime());
				employee.setRole(account.getRole());
				
				list.add(employee);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Employee getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			AccountDAO accountDAO = new AccountDAO();
			Employee employee = null;
			if (rs.next()) {				
				Account account = accountDAO.getById(id);
				
				employee = new Employee();
				
				employee.setId(id);
				employee.setSalary(rs.getFloat(2));
				employee.setWorking(rs.getBoolean(3));
				
				employee.setUserName(account.getUserName());
				employee.setEmail(account.getEmail());
				employee.setPassword(account.getPassword());
				employee.setDateOfBirth(account.getDateOfBirth());
				employee.setGender(account.getGender());
				employee.setPhoneNumber(account.getPhoneNumber());
				employee.setDeleted(account.isDeleted());
				employee.setCreatedTime(account.getCreatedTime());
				employee.setRole(account.getRole());
			}
			statement.close();
			connection.close();
			return employee;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public int insert(Employee employee) {
//		// do something
//		return 1;
//	}
//	
//	public int update(Employee employee) {
//		// do something
//		return 1;
//	}
//	
//	public int delete(int id) {
//		// do something
//		return 1;
//	}
}
