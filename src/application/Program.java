package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc1 = new Scanner(System.in);
		
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
		System.out.printf("Inserted! New id = %d\n", newSeller.getId());
		
		System.out.println("\n!!! *TEST 5: seller update* !!!");
		seller = sellerDao.findById(1);
		seller.setName("Marta Wayne");
		seller.setEmail("martaw@gmail.com");
		sellerDao.update(seller);
		System.out.println("Update done!");
		
		System.out.println("\n!!! *TEST 6: seller delete* !!!");
		System.out.print("Enter seller id to delete: ");
		sellerDao.deleteById(sc1.nextInt());
		System.out.println("Deleted!");
		
		
		
		System.out.println("Congratulations!");
		
		sc1.close();
	}

}
