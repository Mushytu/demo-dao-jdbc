package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBCImpl implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBCImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement pst = null;

		try {
			pst = conn.prepareStatement(
					"INSERT into department " 
					+ "(Name) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());

			if (pst.executeUpdate() > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement pst = null;

		try {
			pst = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ?");
			
			pst.setString(1, obj.getName());
			pst.setInt(2, obj.getId());
			
			pst.executeUpdate();
			
		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement(
					"DELETE FROM department "
					+ " WHERE Id = ?");
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
		} catch( SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				Department tempDep = new Department();
				tempDep.setId(rs.getInt("Id"));
				tempDep.setName(rs.getString("Name"));

				return tempDep;
			}
			return null;
		} catch( SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		return null;
	}

}
