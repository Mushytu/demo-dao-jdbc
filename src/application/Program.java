package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		Seller sll = new Seller(12, "Nelio", "nelio@gmail.com", new Date(), 3000.00, obj);
		
		System.out.println(sll);

	}

}
