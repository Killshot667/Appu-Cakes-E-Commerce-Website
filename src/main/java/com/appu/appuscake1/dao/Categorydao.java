package com.appu.appuscake1.dao;

import com.appu.appuscake1.model.Category;
import com.appu.appuscake1.model.Product;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class Categorydao {

    @Autowired
    JdbcTemplate jt;

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Category";
        return jt.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public Category getCategoryByID(int id) {
        try {
            String sql = "select * from Category where id = ?";
            return (Category) jt.queryForObject(sql, new Object[] { id },
                    new BeanPropertyRowMapper<>(Category.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Category category){
        String sql="insert into Category (name,discount) values (?,?)";
        jt.update(sql,category.getName(),category.getDiscount());
    }

    public void update(Category category) {
        String sql="update Category set name=?, discount=? where id=?";
        jt.update(sql,category.getName(),category.getDiscount(),category.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Category WHERE id = ?";
        jt.update(sql, id);
    }

}
