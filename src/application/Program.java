package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("!!! *TEST 1: seller findById* !!!");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n!!! *TEST 2: seller findByDepartment* !!!");
		List<Seller> list = sellerDao.findByDepartment(new Department(2, null));
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n!!! *TEST 3: seller findAll* !!!");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n!!! *TEST 4: seller insert* !!!");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.printf("Inserted! New id = %d", newSeller.getId());
	}

}
