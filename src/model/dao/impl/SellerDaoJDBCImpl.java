package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBCImpl implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBCImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {

	}

	@Override
	public void update(Seller obj) {

	}

	@Override
	public void deleteById(Seller obj) {

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ? ");
			
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				Department tempDep = new Department();
				tempDep.setId(rs.getInt("DepartmentId"));
				tempDep.setName(rs.getString("DepName"));
				
				Seller tempSll = new Seller();
				tempSll.setId(rs.getInt("Id"));
				tempSll.setName(rs.getString("Name"));
				tempSll.setEmail(rs.getString("Email"));
				tempSll.setBaseSalary(rs.getDouble("BaseSalary"));
				tempSll.setBirthdate(rs.getDate("BirthDate"));
				tempSll.setDepartment(tempDep);
				
				return tempSll;
			
			}
			return null;
			
		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {

		return null;
	}

}
