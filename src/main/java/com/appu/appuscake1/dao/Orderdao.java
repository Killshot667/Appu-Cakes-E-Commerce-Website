package com.appu.appuscake1.dao;

import com.appu.appuscake1.helper.PreparedStatementUtil;
import com.appu.appuscake1.model.Order;
import com.appu.appuscake1.model.OrderItem;
import com.appu.appuscake1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class Orderdao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    public Order save(Order order){
        String sql="insert into Order (customerid,order_date,payment_date,status,cost,mode) values (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] { "id" });
                preparedStatementUtil.setParameters(preparedStatement, order.getCustomerid(),order.getOrder_date(),order.getPayment_date(),
                order.getStatus(),order.getCost(),order.getMode());
                return preparedStatement;
            }
        }, keyHolder);
        int orderId = keyHolder.getKey().intValue();
        order.setId(orderId);
        return order;
    }

    public void saveOrderItem(OrderItem orderItem){
        String sql="insert into OrderItem (orderid,productid,quantity) values (?,?,?)";
        jt.update(sql,orderItem.getOrderid(),orderItem.getProductid(),orderItem.getQuantity());
    }

    public List<Order> getAllCurrentOrders(int id) {
        String sql = "SELECT * FROM Order where customerid =  ?";
        return jt.query(sql,new Object[] { id }, new BeanPropertyRowMapper<>(Order.class));

    }

    public List<OrderItem> getAllOrderItems(int id) {
        String sql = "SELECT * FROM OrderItem where orderid =  ?";
        return jt.query(sql,new Object[] { id }, new BeanPropertyRowMapper<>(OrderItem.class));

    }



}
