package com.appu.appuscake1.dao;

import com.appu.appuscake1.model.Cart;
import com.appu.appuscake1.model.Category;
import com.appu.appuscake1.model.Product;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class Userdao {

    @Autowired
    JdbcTemplate jt;


    public void save(User user){
        String sql="insert into User (name,email,password,role,contactNo,address,city,houseno,pincode) values (?,?,?,?,?,?,?,?,?)";
        jt.update(sql,user.getName(),user.getEmail(),user.getPassword(),user.getRole(),user.getContactNo(),user.getAddress(),user.getCity(),user.getHouseno(),user.getPincode());

    }

    public User getUserByEmail(String email) {
        try {
            String sql = "select * from User where email = ?";
            return (User) jt.queryForObject(sql, new Object[] { email },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getUserByID(int id) {
        try {
            String sql = "select * from User where id = ?";
            return (User) jt.queryForObject(sql, new Object[] { id },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> getAllCustomers() {
        String sql = "SELECT * FROM User where role = 'ROLE_USER'";
        return jt.query(sql, new BeanPropertyRowMapper<>(User.class));
    }



    public void update(User user) {
        String sql="update User set name=?, email=?, password=?, role=?, contactNo=?, address=?, city=?, houseno=?, pincode=? where id=?";
        jt.update(sql,user.getName(),user.getEmail(),user.getPassword(),user.getRole(),user.getContactNo(),user.getAddress(),user.getCity(),user.getHouseno(),user.getPincode(),user.getId());
    }


    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        jt.update(sql, id);
    }

    public List<User> getUsersByMail(String email) {
        String sql = "SELECT * FROM User where email = ?";
        return jt.query(sql,new Object[] { email }, new BeanPropertyRowMapper<>(User.class));
    }





}