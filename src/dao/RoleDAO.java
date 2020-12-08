package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Account;
import model.Role;

public class RoleDAO extends DAO {
	private static final String SELECT_ALL_SQL_STATEMENT = "select * from tblRole";
	private static final String SELECT_ONE_SQL_STATEMENT = "select * from tblRole where id = ?";
	private static final String INSERT_SQL_STATEMENT = "insert into tblRole(role_name, description, is_deleted) values (?,?,?)";
	
	public ArrayList<Role> getAll() {
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_SQL_STATEMENT);
			ArrayList<Role> list = new ArrayList<>();
			
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt(1));
				role.setRoleName(rs.getString(2));
				role.setDescription(rs.getString(3));
				role.setDeleted(rs.getBoolean(4));
				list.add(role);
			}
			statement.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Role getById(int id) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL_STATEMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			Role role = null;
			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt(1));
				role.setRoleName(rs.getString(2));
				role.setDescription(rs.getString(3));
				role.setDeleted(rs.getBoolean(4));
			}
			statement.close();
			connection.close();
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Role role) {
		try {
			Connection connection = this.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, role.getRoleName());
			statement.setString(2, role.getDescription());
			statement.setBoolean(3, role.isDeleted());

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
	
//	public int update(Role role) {
//		// do something
//	}
//	
//	public int delete(int id) {
//		// do something
//	}
}
