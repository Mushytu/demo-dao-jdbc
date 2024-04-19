package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n!!! *TEST 1: Department insert* !!!");
		Department newDep = new Department(null, "Beauty");
		depDao.insert(newDep);
		System.out.printf("Inserted! New id = %d\n", newDep.getId());
	}

}