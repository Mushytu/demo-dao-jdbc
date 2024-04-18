package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

		return null;
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
