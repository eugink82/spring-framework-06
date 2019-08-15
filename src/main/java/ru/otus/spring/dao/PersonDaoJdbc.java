package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class PersonDaoJdbc implements PersonDao {
    private final NamedParameterJdbcOperations namedParameterJdbc;
    private final JdbcOperations jdbc;
    private static final BeanPropertyRowMapper<Person> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Person.class);

    @Autowired
    public PersonDaoJdbc(NamedParameterJdbcOperations namedParameterJdbc) {
        this.jdbc=namedParameterJdbc.getJdbcOperations();
        this.namedParameterJdbc = namedParameterJdbc;
    }


    public int count() {
        return jdbc.queryForObject("SELECT count(*) FROM persons",Integer.class);
    }

    public void insert(Person p){
       BeanPropertySqlParameterSource map=new BeanPropertySqlParameterSource(p);
        namedParameterJdbc.update("INSERT INTO persons(id,name) values(:id,:name)",map);
    }


    public List<Person> getAll() {
        return namedParameterJdbc.query("select * from Persons",new PersonMapper());
    }


    public Person getById(int id) {
        Person p=new Person();
        p.setId(id);
        BeanPropertySqlParameterSource map=new BeanPropertySqlParameterSource(p);
       return namedParameterJdbc.queryForObject("select * from persons where id=:id",map, new PersonMapper());

    }

    private static class PersonMapper implements RowMapper<Person>{


        public Person mapRow(ResultSet rs, int i) throws SQLException {
            int id=rs.getInt("id");
            String name=rs.getString("name");
            return new Person(id,name);
        }
    }

    public void delete(int id) {
        MapSqlParameterSource map=new MapSqlParameterSource()
                .addValue("id",id);
        namedParameterJdbc.update("delete from Persons where id=:id",map);
    }
}
