package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Account;
import model.Invoice;

public class InvoiceDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblInvoice";
	private static final String SELECT_WAITING_SQL_STATEMENT = "select * from tblInvoice where status = 'WAITING'";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblInvoice where id = ?";
	private static final String INSERT_SQL_STATEMENT = "insert into tblInvoice(created_time, customer_id, status, export_employee_id, is_deleted)"
			+ " values (?,?,?,?,?)";
	private static final String UPDATE_SQL_STATEMENT = "update tblInvoice set created_time = ?, customer_id = ?, status = ?, export_employee_id = ?, "
			+ "is_deleted = ? where id = ?";
	
	public ArrayList<Invoice> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Invoice> list = new ArrayList<>();
			CustomerDAO customerDAO = new CustomerDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			while (rs.next()) {
				Invoice invoice = new Invoice();
				invoice.setId(rs.getInt(1));
				invoice.setCreatedTime(rs.getTimestamp(2));
				invoice.setCustomer(customerDAO.getById(rs.getInt(3)));
				invoice.setStatus(rs.getString(4));
				invoice.setExportEmployee(employeeDAO.getById(rs.getInt(5)));
				invoice.setDeleted(rs.getBoolean(6));
				list.add(invoice);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public ArrayList<Invoice> getWaiting() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_WAITING_SQL_STATEMENT);
			ArrayList<Invoice> list = new ArrayList<>();
			CustomerDAO customerDAO = new CustomerDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			while (rs.next()) {
				Invoice invoice = new Invoice();
				invoice.setId(rs.getInt(1));
				invoice.setCreatedTime(rs.getTimestamp(2));
				invoice.setCustomer(customerDAO.getById(rs.getInt(3)));
				invoice.setStatus(rs.getString(4));
				invoice.setExportEmployee(employeeDAO.getById(rs.getInt(5)));
				invoice.setDeleted(rs.getBoolean(6));
				list.add(invoice);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	
	public Invoice getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			CustomerDAO customerDAO = new CustomerDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			Invoice invoice = null;
			if (rs.next()) {
				invoice = new Invoice();
				invoice.setId(rs.getInt(1));
				invoice.setCreatedTime(rs.getTimestamp(2));
				invoice.setCustomer(customerDAO.getById(rs.getInt(3)));
				invoice.setStatus(rs.getString(4));
				invoice.setExportEmployee(employeeDAO.getById(rs.getInt(5)));
				invoice.setDeleted(rs.getBoolean(6));
			}
			statement.close();
			connection.close();
			return invoice;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Invoice invoice) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            Timestamp createdTime = invoice.getCreatedTime() == null ? null : new Timestamp(invoice.getCreatedTime().getTime());

			statement.setTimestamp(1, createdTime);
			statement.setInt(2, invoice.getCustomer().getId());
			statement.setString(3, invoice.getStatus());
			if (invoice.getExportEmployee() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {				
				statement.setInt(4, invoice.getExportEmployee().getId());
			}
			statement.setBoolean(5, invoice.isDeleted());

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
	
	public int update(Invoice invoice) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_SQL_STATEMENT);
            Timestamp createdTime = invoice.getCreatedTime() == null ? null : new Timestamp(invoice.getCreatedTime().getTime());

			statement.setTimestamp(1, createdTime);
			statement.setInt(2, invoice.getCustomer().getId());
			statement.setString(3, invoice.getStatus());
			if (invoice.getExportEmployee() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {				
				statement.setInt(4, invoice.getExportEmployee().getId());
			}
			statement.setBoolean(5, invoice.isDeleted());
			statement.setInt(6, invoice.getId());

			int result = statement.executeUpdate();
			
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
//	public int delete(int id) {
//		// do something
//		return 1;
//	}
}
