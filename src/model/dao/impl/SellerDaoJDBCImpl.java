package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			
			if (pst.executeUpdate() > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			pst.setInt(6, obj.getId());
			
			pst.executeUpdate();

		}
		catch (SQLException e1) {
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
					"DELETE FROM seller "
					+ "WHERE Id = ?");
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ? ");

			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				
				Department tempDep = instantiateDepartment(rs);
				Seller tempSeller = instantiateSeller(rs, tempDep);

				return tempSeller;
			}
			return null;

		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
			
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department tempDep) throws SQLException {
		Seller tempSeller = new Seller();
		tempSeller.setId(rs.getInt("Id"));
		tempSeller.setName(rs.getString("Name"));
		tempSeller.setEmail(rs.getString("Email"));
		tempSeller.setBaseSalary(rs.getDouble("BaseSalary"));
		tempSeller.setBirthdate(rs.getDate("BirthDate"));
		tempSeller.setDepartment(tempDep);

		return tempSeller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department tempDep = new Department();
		tempDep.setId(rs.getInt("DepartmentId"));
		tempDep.setName(rs.getString("DepName"));

		return tempDep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = pst.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department tempDep = map.get(rs.getInt("DepartmentId"));
				
				if(tempDep == null) {
					
					tempDep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), tempDep);
				}
				
				Seller tempSeller = instantiateSeller(rs, tempDep);
				sellerList.add(tempSeller);
			}
			return sellerList;
			
		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
			
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			pst.setInt(1, department.getId());
			
			rs = pst.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				
				Department tempDep = map.get(rs.getInt("DepartmentId"));
				
				if(tempDep == null) {
					
					tempDep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), tempDep);
				}
				
				Seller tempSeller = instantiateSeller(rs, tempDep);
				sellerList.add(tempSeller);
			}
			return sellerList;
			
		} catch (SQLException e1) {
			throw new DbException(e1.getMessage());
			
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

}
