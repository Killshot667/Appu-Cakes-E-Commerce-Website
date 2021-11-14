package com.appu.appuscake1.dao;

import com.appu.appuscake1.model.Cart;
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
public class Productdao {

    @Autowired
    JdbcTemplate jt;

    public List<Product> getAllAvailableProducts() {
        String sql = "SELECT * FROM Product where availability > 0";
        List<Product> products = jt.query(sql, new BeanPropertyRowMapper<>(Product.class));
        return products;
    }

    public List<Cart> getAllAvailableCarts(int id) {
        String sql = "SELECT * FROM Cart where customerid = ?";
        return jt.query(sql,new Object[] { id }, new BeanPropertyRowMapper<>(Cart.class));

    }

    public List<Product> getAllAvailableProductsPattern(String searchBar) {
        String pattern = "%" + searchBar + "%";
        String sql = "SELECT * FROM Product where availability > 0 and name like ?";
        List<Product> products = jt.query(sql,new Object[] { pattern }, new BeanPropertyRowMapper<>(Product.class));
        return products;
    }

    public List<Product> getAllAvailableProductsPatternMulti(String searchBar) {
        String pattern = "%" + searchBar + "%";
        String sql = "SELECT distinct p.id , p.name , p.description , p.prodImage, p.discount, p.categoryid, p.price, p.availability FROM Product as p,Category as c where p.availability > 0 and ((p.name like ?) or (c.name like ?))";
        List<Product> products = jt.query(sql,new Object[] { pattern,pattern }, new BeanPropertyRowMapper<>(Product.class));
        return products;
    }


//    public List<Cart> getCartbyID(String customerid){
//        String sql="select * from Cart where CustomerID=?";
//        return jt.query(sql, new Object[]{customerid},new RowMapper<Cart>(){
//            public Cart mapRow(ResultSet row, int rowNum) throws SQLException {
//                Cart cart = new Cart();
//                cart.setCustomerID(row.getString("CustomerID"));
//                cart.setProductid(row.getInt("ProductID"));
//                cart.setQuantity(row.getInt("Quantity"));
//                return cart;
//            }
//        });
//    }

    public List<Integer> getAllAvailableProductsIds() {
        String sql = "SELECT id FROM Product where availability > 0";
        return jt.queryForList(sql, int.class);
    }

    public Product getProductByID(int id) {
        try {
            String sql = "select * from Product where id = ?";
            return (Product) jt.queryForObject(sql, new Object[] { id },
                    new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Integer> getProductsInCart(int id) {
        String sql = "SELECT productid FROM Cart where customerid = ?";
        return jt.queryForList(sql, new Object[] { id }, int.class);
    }


    public void save(Product product){
        String sql="insert into Product (name,categoryid,price,description,prodImage,discount,availability) values (?,?,?,?,?,?,?)";
        jt.update(sql,product.getName(),product.getCategoryid(),product.getPrice(),product.getDescription(),product.getProdImage(),product.getDiscount(),product.getAvailability());
    }

    public void update(Product product) {
        String sql="update Product set name=?, categoryid=?, price=?, description=?, prodImage=?, discount=?, availability=? where id=?";
        jt.update(sql,product.getName(),product.getCategoryid(),product.getPrice(),product.getDescription(),product.getProdImage(),product.getDiscount(),product.getAvailability(),product.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jt.update(sql, id);
    }

    public void addToCart(int customerid,int productid)
    {
        int quantity = 1;
        String sql="insert into Cart (customerid,productid,quantity) values (?,?,?)";
        jt.update(sql,customerid,productid,quantity);
    }

    public void deleteFromCart(int customerid,int productid)
    {
        String sql = "DELETE FROM Cart WHERE customerid = ? and productid = ?";
        jt.update(sql, customerid, productid);
    }

    public void updateCart(int customerid, int productid, int quantity)
    {
        String sql="update Cart set quantity = ? where customerid = ? and productid = ?";
        jt.update(sql,quantity,customerid,productid);
    }

    public void updateQuantity(int id,int quantity) {
        String sql="update Product set availability = availability - ? where id = ?";
        jt.update(sql,quantity,id);
    }

    public void deleteEntireCart(int id)
    {
        String sql = "DELETE FROM Cart WHERE customerid = ?";
        jt.update(sql, id);
    }




}
