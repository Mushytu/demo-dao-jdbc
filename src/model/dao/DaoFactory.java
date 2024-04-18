package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBCImpl;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBCImpl(DB.getConnection());
	}
}
