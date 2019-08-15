package ru.otus.spring.dao;

import ru.otus.spring.domain.Person;

import java.util.List;

public interface PersonDao {
    int count();
    void insert(Person p);
    List<Person> getAll();
    Person getById(int id);
    void delete(int id);
}
