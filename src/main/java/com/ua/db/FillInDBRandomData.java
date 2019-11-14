package com.ua.db;

import com.ua.entity.Degree;
import com.ua.entity.Department;
import com.ua.entity.Lector;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FillInDBRandomData {
    private static final String FULL_NAME_FILE_PATH = "src/main/resources/META-INF/full_name.txt";
    private static final String DEPARTMENT_FILE_PATH = "src/main/resources/META-INF/department.txt";
    private Random random;
    private EntityManager entityManager;

    public FillInDBRandomData(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.random = new Random();
    }

    public void fillInRandomData() {
        if(entityManager.createQuery("SELECT lector FROM Lector lector", Lector.class).getResultList().size()==0){
            addRandomLector();
        }
        if (entityManager.createQuery("SELECT degree FROM Degree degree", Degree.class).getResultList().size()==0){
            createDegree("Assistant");
            createDegree("Associate professor");
            createDegree("Professor");
            addDegreeToLector();
        }
        if(entityManager.createQuery("SELECT department FROM Department department", Department.class).getResultList().size()==0){
            addRandomDepartment();
            addLectorsToDepartment();
        }
    }

    private void addRandomLector() {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(FULL_NAME_FILE_PATH))) {
            String fullName = null;
            while ((fullName = inputStream.readLine()) != null) {
                Lector lector = new Lector();
                lector.setFullName(fullName);
                lector.setSalary((double) (3000 + random.nextInt(7000)));
                entityManager.persist(lector);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRandomDepartment() {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(DEPARTMENT_FILE_PATH))) {
            String name = null;
            while ((name = inputStream.readLine()) != null) {
                Department department = new Department();
                department.setName(name);
                entityManager.persist(department);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDegree(String name) {
        Degree degree = new Degree();
        degree.setName(name);
        entityManager.persist(degree);
    }

    private void addDegreeToLector() {
        List<Degree> degrees = entityManager.createQuery("SELECT degree FROM Degree degree", Degree.class).getResultList();
        List<Lector> lectors = entityManager.createQuery("SELECT lector FROM Lector lector", Lector.class).getResultList();
        int countDegree = degrees.size();
        for (Lector lector : lectors) {
            lector.setDegree(degrees.get(random.nextInt(countDegree)));
            entityManager.persist(lector);
        }
    }

    private void addLectorsToDepartment() {
        List<Lector> lectors = entityManager.createQuery("SELECT lector FROM Lector lector", Lector.class).getResultList();
        List<Department> departments = entityManager.createQuery("SELECT department FROM Department department", Department.class).getResultList();
        for (Department department : departments) {
            int countLectors = random.nextInt(lectors.size());
            List<Integer> lectorsId = new ArrayList<>();
            for (int i = 0; i < countLectors; i++) {
                int lectorId = random.nextInt(lectors.size());
                if (!lectorsId.contains(lectorId)) {
                    lectorsId.add(lectorId);
                }
            }
            List<Lector> lectorsInDepartament = new ArrayList<>();
            for (int id : lectorsId) {
                lectorsInDepartament.add(lectors.get(id));
            }
            department.setHead(lectorsInDepartament.get(random.nextInt(lectorsInDepartament.size())));
            department.setLectors(lectorsInDepartament);
            entityManager.persist(department);
        }
    }
}
