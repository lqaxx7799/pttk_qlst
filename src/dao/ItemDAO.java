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
import model.Item;
import model.ItemCategory;

public class ItemDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblItem";
	private static final String SELECT_LIKE_NAME_SQL_STATEMENT = "select * from tblItem where item_name like CONCAT( '%',?,'%')";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblItem where id = ?";
	private static final String INSERT_SQL_STATEMENT = "insert into tblItem(item_name, description, created_time, is_deleted, unit, unit_price, "
			+ "tblCategory_id, picture_url) values (?,?,?,?,?,?,?,?)";
	private static final String UPDATE_SQL_STATEMENT = "update tblItem set item_name = ?, description = ?, created_time = ?, is_deleted = ?, unit = ?, "
			+ "unit_price = ?, tblCategory_id = ?, picture_url = ? where id = ?";
	
	public ArrayList<Item> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Item> list = new ArrayList<>();
			
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt(1));
				item.setItemName(rs.getString(2));
				item.setDescription(rs.getString(3));
				item.setCreatedTime(rs.getTimestamp(4));
				item.setDeleted(rs.getBoolean(5));
				item.setUnit(rs.getString(6));
				item.setUnitPrice(rs.getFloat(7));
				item.setItemCategory(new ItemCategory());
				item.setPictureUrl(rs.getString(9));
				list.add(item);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public ArrayList<Item> getByName(String name) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_LIKE_NAME_SQL_STATEMENT);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			ArrayList<Item> list = new ArrayList<>();
			
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt(1));
				item.setItemName(rs.getString(2));
				item.setDescription(rs.getString(3));
				item.setCreatedTime(rs.getTimestamp(4));
				item.setDeleted(rs.getBoolean(5));
				item.setUnit(rs.getString(6));
				item.setUnitPrice(rs.getFloat(6));
				item.setItemCategory(new ItemCategory());
				item.setPictureUrl(rs.getString(8));
				list.add(item);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Item getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			Item item = null;
			if (rs.next()) {
				item = new Item();
				item.setId(rs.getInt(1));
				item.setItemName(rs.getString(2));
				item.setDescription(rs.getString(3));
				item.setCreatedTime(rs.getTimestamp(4));
				item.setDeleted(rs.getBoolean(5));
				item.setUnit(rs.getString(6));
				item.setUnitPrice(rs.getFloat(7));
				item.setItemCategory(new ItemCategory());
				item.setPictureUrl(rs.getString(9));
			}
			statement.close();
			connection.close();
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Item item) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            Timestamp createdTime = item.getCreatedTime() == null ? null : new Timestamp(item.getCreatedTime().getTime());

			statement.setString(1, item.getItemName());
			statement.setString(2, item.getDescription());
			statement.setTimestamp(3, createdTime);
			statement.setBoolean(4, item.isDeleted());
			statement.setString(5, item.getUnit());
			statement.setFloat(6, item.getUnitPrice());
			statement.setNull(7, java.sql.Types.INTEGER);
			statement.setString(8, item.getPictureUrl());

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
	
	public int update(Item item) {
		// do something
		return 1;
	}
	
	public int delete(int id) {
		// do something
		return 1;
	}
}
