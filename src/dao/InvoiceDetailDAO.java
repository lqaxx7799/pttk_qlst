package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Invoice;
import model.InvoiceDetail;

public class InvoiceDetailDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblInvoiceDetail";
	private static final String SELECT_BY_INVOICE_ID_SQL_STATEMENT = "select * from tblInvoiceDetail where tblInvoice_id = ?";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblInvoiceDetail where id = ?";
	private static final String INSERT_SQL_STATEMENT = "insert into tblInvoiceDetail(tblInvoice_id, tblItem_id, unit_price, quantity)"
			+ " values (?,?,?,?)";
	private static final String UPDATE_SQL_STATEMENT = "update tblInvoiceDetail set tblInvoice_id = ?, tblItem_id = ?, unit_price = ?, quantity = ? "
			+ "where id = ?";
	
	public ArrayList<InvoiceDetail> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<InvoiceDetail> list = new ArrayList<>();
			InvoiceDAO invoiceDAO = new InvoiceDAO();
			ItemDAO itemDAO = new ItemDAO();
			
			while (rs.next()) {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				invoiceDetail.setId(rs.getInt(1));
				invoiceDetail.setInvoice(invoiceDAO.getById(rs.getInt(2)));
				invoiceDetail.setItem(itemDAO.getById(rs.getInt(3)));
				invoiceDetail.setUnitPrice(rs.getFloat(4));
				invoiceDetail.setQuantity(rs.getInt(5));
				list.add(invoiceDetail);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public ArrayList<InvoiceDetail> getByInvoiceId(int invoiceId) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_INVOICE_ID_SQL_STATEMENT);
			statement.setInt(1, invoiceId);
			ResultSet rs = statement.executeQuery();
			ArrayList<InvoiceDetail> list = new ArrayList<>();
			InvoiceDAO invoiceDAO = new InvoiceDAO();
			ItemDAO itemDAO = new ItemDAO();
			
			while (rs.next()) {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				invoiceDetail.setId(rs.getInt(1));
				invoiceDetail.setInvoice(invoiceDAO.getById(rs.getInt(2)));
				invoiceDetail.setItem(itemDAO.getById(rs.getInt(3)));
				invoiceDetail.setUnitPrice(rs.getFloat(4));
				invoiceDetail.setQuantity(rs.getInt(5));
				list.add(invoiceDetail);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public InvoiceDetail getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			InvoiceDAO invoiceDAO = new InvoiceDAO();
			ItemDAO itemDAO = new ItemDAO();
			
			InvoiceDetail invoiceDetail = null;
			if (rs.next()) {
				invoiceDetail = new InvoiceDetail();
				invoiceDetail.setId(rs.getInt(1));
				invoiceDetail.setInvoice(invoiceDAO.getById(rs.getInt(2)));
				invoiceDetail.setItem(itemDAO.getById(rs.getInt(3)));
				invoiceDetail.setUnitPrice(rs.getFloat(4));
				invoiceDetail.setQuantity(rs.getInt(5));
			}
			statement.close();
			connection.close();
			return invoiceDetail;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(InvoiceDetail invoiceDetail) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, invoiceDetail.getInvoice().getId());
			statement.setInt(2, invoiceDetail.getItem().getId());
			statement.setFloat(3, invoiceDetail.getUnitPrice());
			statement.setInt(4, invoiceDetail.getQuantity());

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
	
	public int update(InvoiceDetail invoiceDetail) {
		// do something
		return 1;
	}
	
	public int delete(int id) {
		// do something
		return 1;
	}
}
