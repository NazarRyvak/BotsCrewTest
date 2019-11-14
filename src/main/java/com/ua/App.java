package com.ua;

import com.ua.db.FillInDBRandomData;
import com.ua.utils.Console;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("mysql");
        EntityManager em = managerFactory.createEntityManager();
        em.getTransaction().begin();

        FillInDBRandomData fillInDBRandomData = new FillInDBRandomData(em);
        fillInDBRandomData.fillInRandomData();

        Console console = new Console(em);
        console.consoleMenu();

        em.getTransaction().commit();
        em.close();
        managerFactory.close();
    }
}
