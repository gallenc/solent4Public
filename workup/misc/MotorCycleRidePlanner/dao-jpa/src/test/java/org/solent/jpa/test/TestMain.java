package org.solent.jpa.test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;
import org.solent.jpa.test.Employee;

public class TestMain {
	private static final String PERSISTENCE_UNIT_NAME = "motorcyclePersistence";
	private static EntityManagerFactory factory;
	
	@Test
	public void testjpa() {
		startJpa();
	}
	
	public static void main(String[] args) {
		TestMain t = new TestMain();
			t.startJpa();
	}

	public void startJpa() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select e from Employee e");
		List<Employee> employeeList = q.getResultList();
		for (Employee employee : employeeList) {
			System.out.println(employee);
		}
		System.out.println("Size: " + employeeList.size());
		// create new todo
		em.getTransaction().begin();
		Employee emp = new Employee();
		emp.setName("Mukesh");
		emp.setEmail("m@gmail.com");
		emp.setDepartment("Finance");
		em.persist(emp);
		em.getTransaction().commit();
		em.close();
	}
}