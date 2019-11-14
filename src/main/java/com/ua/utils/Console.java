package com.ua.utils;

import com.ua.db.FillInDBRandomData;
import com.ua.db.Query;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Console {

    private Scanner sc;
    private FillInDBRandomData fillInDBRandomData;
    private Query query;

    public Console(EntityManager entityManager) {
        sc = new Scanner(System.in);
        this.fillInDBRandomData = new FillInDBRandomData(entityManager);
        this.query = new Query(entityManager);
    }

    public void consoleMenu() {
        fillInDBRandomData.fillInRandomData();
        while (true) {
            showCommands();
            String command = sc.nextLine();
            switch (command) {
                case "1": {
                    String department = enterDepartment();
                    query.printHeadDepartment(department);
                    break;
                }
                case "2": {
                    String department = enterDepartment();
                    query.printDepartmentStatistic(department);
                    break;
                }
                case "3": {
                    String department = enterDepartment();
                    query.printAverageSalaryDepartment(department);
                    break;
                }
                case "4": {
                    String department = enterDepartment();
                    query.printCountEmployee(department);
                    break;
                }
                case "5": {
                    System.out.println("Please enter pattern:");
                    query.globalSearch(sc.nextLine());
                    break;
                }
                case "6": {
                    System.out.println("Goodbye!!!");
                    System.exit(0);
                }
                default: {
                    System.out.println();
                    System.out.println("Incorrect data! Please enter correct command: ");
                }

            }
        }
    }

    private void showCommands() {
        System.out.println();
        System.out.println("Who is head of department? Enter: 1");
        System.out.println("Show department statistic. Enter: 2");
        System.out.println("Show the average salary for department. Enter: 3");
        System.out.println("Show count of employee for department. Enter: 4");
        System.out.println("Global search. Enter: 5");
        System.out.println("Break. Enter: 6");
    }

    private String enterDepartment() {
        System.out.println("Please enter dapartment`s name:");
        return sc.nextLine();
    }
}
