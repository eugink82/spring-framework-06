package ru.otus.spring;

//import org.h2.tools.Console;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.dao.PersonDao;
import ru.otus.spring.domain.Person;


public class Main {

    public static void main(String[] args) throws Exception {

        //ApplicationContext context = SpringApplication.run(Main.class);
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/spring-app.xml",
                "spring/spring-db.xml");

        PersonDao dao = context.getBean(PersonDao.class);
        System.out.println("All count " + dao.count());

        //dao.insert(new Person(6, "Brooni"));

        System.out.println("All count " + dao.count());

        Person ivan = dao.getById(2);

        for(Person p : dao.getAll()){
            System.out.println(p.getId()+", "+p.getName());
        }

        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());
        dao.delete(4);
        for(Person p : dao.getAll()){
            System.out.println(p.getId()+", "+p.getName());
        }
       // Console.main(args);
    }
}
