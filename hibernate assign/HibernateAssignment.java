package com.te.hibernateDemo.dao;

import java.util.List;
import java.util.Scanner;

import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.te.hibernate.bean.Employee;

public class HibernateAssignment {
	public static void main(String[] args) {
		Employee employee = new Employee();
		Scanner scanner = new Scanner(System.in);

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("emp");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		while (true) {
			System.out.println("If You want to Display the Data from The DataBase Press\nYes Or No ");
			String input = scanner.next();
			if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {

				System.out.println(
						"Please Select the Option To Display\n1.Display Data All Table Data \n2.Display  Particular Employee Data By ID\n3.Update Data Of Particular Employee\n4.To Delete Particular Employee Data From The DataBase\n5.Exit (Please Exit If You Dont Want Perform Any Operations)");
				System.out.println("----------------------------------------------------------------------");
				String choice = scanner.next();
				switch (choice) {

				case "1":
					Query query = manager.createQuery("from Employee");
					List<Employee> list = query.getResultList();
					System.out.println("\n Dispaying All The Employee Details");
					for (Employee out : list) {
						System.out.println(out);
					}

					System.out.println("-------------------------------------------\n");
					break;
				case "2":
					System.out.println("Enter the Id To Display The Employee Details");
					int id = scanner.nextInt();
					employee = manager.find(Employee.class, id);
					if (manager.contains(employee)) {
						query = manager.createQuery("from Employee where id=:i");

						query.setParameter("i", id);
						employee = (Employee) query.getSingleResult();
						System.out.println("-----------Displaying Data By Id -------------");
						System.out.println(employee);
					} else {
						try {
							throw new EmployeeDeatilsNotFoundException("Given Employee Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}

					System.out.println("-------------------------------------------\n");
					
					break;
				case "3":
					String newName = null;
					double newSalary = 0;
					String newDesig = null;
					String choiceOne = null;
					System.out.println("Enter the Id To Update The Employee Details");
					id = scanner.nextInt();
					if (manager.contains(manager.find(Employee.class, id))) {
						employee=manager.find(Employee.class, id);
						System.out.println("Do You Want to update Employee Name ( yes or no)");
						choiceOne = scanner.next();
						if (choiceOne.equalsIgnoreCase("yes") || choiceOne.equalsIgnoreCase("y")) {
							System.out.println("Enter the New Name To Update For The Employee Id: " + id);
							newName = scanner.next();
			
						}else {
							newName=employee.getName();
						}
						System.out.println("Do You Want to Update Employee Salary ( yes or no)");
						choiceOne = scanner.next();
						if (choiceOne.equalsIgnoreCase("yes") || choiceOne.equalsIgnoreCase("y")) {
							System.out.println("Enter the New Salary To Update For The Employee Id: " + id);
							newSalary = scanner.nextDouble();
						}else {
							newSalary=employee.getSalary();
						}
						System.out.println("Do You Want to Update Employee Designation ( yes or no)");
						choiceOne = scanner.next();
						if (choiceOne.equalsIgnoreCase("yes") || choiceOne.equalsIgnoreCase("y")) {
							System.out.println("Enter the New Designation To Update For The Employee Id: " + id);
							newDesig = scanner.next();
						}else {
							newDesig=employee.getDesign();
						}


						if (newName != null && newDesig != null && newSalary != 0) {
							transaction.begin();
							query = manager.createQuery("update Employee set name=:name,design=:desig,salary=:sal where id=:id");
							query.setParameter("name", newName);
							query.setParameter("desig", newDesig);
							query.setParameter("sal", newSalary);
							query.setParameter("id", id);
							
							query.executeUpdate();
							manager.persist(employee);
							transaction.commit();
						}

					} else {
						try {
							throw new EmployeeDeatilsNotFoundException("Given Employee Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());

						}
					}

					System.out.println("-------------------------------------------\n");
					break;
					
				case "4":
					System.out.println("Enter the Id To delete The Employee Details");
					id = scanner.nextInt();
					employee = manager.find(Employee.class, id);
					if (manager.contains(employee)) {
						transaction.begin();
						query = manager.createQuery("delete from Employee where id=:i");

						query.setParameter("i", id);
						query.executeUpdate();
						transaction.commit();

						System.out.println("The Id " + id + " Employee Deatils Deleted Sucessfuly");

					} else {
						try {
							throw new EmployeeDeatilsNotFoundException("Given Employee Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}

					System.out.println("-------------------------------------------\n");

					break;

				case "5":
					System.exit(0);
					break;
				default:

					try {
						throw new EmployeeDeatilsNotFoundException(
								"Please check the input Value...!! and please select the Only  Given Options\n\n");
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			} else {
				if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
					System.exit(0);
				}
				try {
					throw new EmployeeDeatilsNotFoundException("Please Check the Input..!!");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}

		}
	}
}
