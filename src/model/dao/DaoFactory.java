package model.dao;

import model.dao.impl.SellerDaoJDBCImpl;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBCImpl();
	}
}
