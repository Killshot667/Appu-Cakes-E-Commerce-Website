package com.appu.appuscake1.dao;

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
        String sql="insert into User (name,email,password,role,contactNo,address,city,profileImage) values (?,?,?,?,?,?,?,?)";
        jt.update(sql,user.getName(),user.getEmail(),user.getPassword(),user.getRole(),user.getContactNo(),user.getAddress(),user.getCity(),user.getProfileImage());

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



    public void update(User user) {
        String sql="update User set name=?, email=?, password=?, role=?, contactNo=?, address=?, city=?, profileImage=? where id=?";
        jt.update(sql,user.getName(),user.getEmail(),user.getPassword(),user.getRole(),user.getContactNo(),user.getAddress(),user.getCity(),user.getProfileImage(),user.getId());
    }



}