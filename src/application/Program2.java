package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc1 = new Scanner(System.in);
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n!!! *TEST 1: Department insert* !!!");
		Department newDep = new Department(null, "Beauty");
		depDao.insert(newDep);
		System.out.printf("Inserted! New id = %d\n", newDep.getId());
		
		System.out.println("\n!!! *TEST 2: Department update* !!!");
		Department department = depDao.findById(1);
		department.setName("Clothes");
		depDao.update(department);
		System.out.println("Update done!");
		
		System.out.println("\n!!! *TEST 3: Department delete* !!!");
		System.out.print("Enter department id to delete: ");
		depDao.deleteById(sc1.nextInt());
		System.out.println("Deleted!");
		
		System.out.println("\n!!! *TEST 4: Department findById* !!!");
		department = depDao.findById(3);
		System.out.println(department);
		
		System.out.println("\n!!! *TEST 5: Department findAll* !!!");
		List<Department> list = depDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}
		
		
		
		System.out.println("Congratulations!");
		
		sc1.close();
	}

}
