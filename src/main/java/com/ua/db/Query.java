package com.ua.db;

import com.ua.entity.Degree;
import com.ua.entity.Department;
import com.ua.entity.Lector;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class Query {

    private EntityManager entityManager;

    public Query(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void printHeadDepartment(String name) {
        try {
            Department department = findDepartmentByName(name);
            System.out.println();
            System.out.println("Head of " + department.getName() + " is " + department.getHead().getFullName());
        } catch (NoResultException e) {
            printMessageIfDepartmentNotExist(name);
        }
    }

    public void printDepartmentStatistic(String name) {
        try {
            Department department = findDepartmentByName(name);
            List<Degree> degrees = entityManager.createQuery("SELECT degree FROM Degree degree", Degree.class).getResultList();
            for (Degree degree : degrees) {
                int count = 0;
                for (Lector lector : department.getLectors()) {
                    if (lector.getDegree().equals(degree)) {
                        count++;
                    }
                }
                System.out.println();
                System.out.println(degree.getName() + " - " + count);
            }
        } catch (NoResultException e) {
            printMessageIfDepartmentNotExist(name);
        }
    }

    public void printAverageSalaryDepartment(String name) {
        try {
            Department department = findDepartmentByName(name);
            double sum = 0.0;
            for (Lector lector : department.getLectors()) {
                sum += lector.getSalary();
            }
            System.out.println();
            System.out.println("The average salary of " + department.getName() + " is " + String.format("%.2f", sum / department.getLectors().size()));
        } catch (NoResultException e) {
            printMessageIfDepartmentNotExist(name);
        }
    }

    public void printCountEmployee(String name) {
        try {
            int count = entityManager.createQuery("SELECT department FROM Department department WHERE department.name=:name", Department.class).setParameter("name", name).getSingleResult().getLectors().size();
            System.out.println();
            System.out.println(count);
        } catch (NoResultException e) {
            printMessageIfDepartmentNotExist(name);
        }
    }

    public void globalSearch(String pattern) {
        entityManager.createQuery("SELECT lector FROM Lector lector where lector.fullName like :pattern", Lector.class)
                .setParameter("pattern", "%" + pattern + "%").getResultList().forEach(lector -> {
            System.out.println();
            System.out.println(lector.getFullName());
        });
    }

    private void printMessageIfDepartmentNotExist(String name) {
        List<Department> departments = entityManager.createQuery("SELECT department FROM Department department", Department.class).getResultList();
        System.out.println();
        System.out.println("Department: '" + name + "' doesn`t exist");
        System.out.println("Departments:");
        for (Department department : departments) {
            System.out.println(department.getName());
        }
    }

    private Department findDepartmentByName(String name) {
        return entityManager.createQuery("SELECT department FROM Department department WHERE department.name=:name", Department.class)
                .setParameter("name", name).getSingleResult();
    }
}
