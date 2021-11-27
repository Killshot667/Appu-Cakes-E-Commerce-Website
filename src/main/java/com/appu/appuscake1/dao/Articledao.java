package com.appu.appuscake1.dao;

import com.appu.appuscake1.model.Article;
import com.appu.appuscake1.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class Articledao {
    @Autowired
    JdbcTemplate jt;

    public List<Article> getAllArticles() {
        String sql = "SELECT * FROM Article order by id desc";
        return jt.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }

    public Article getArticleByID(int id) {
        try {
            String sql = "select * from Article where id = ?";
            return (Article) jt.queryForObject(sql, new Object[] { id },
                    new BeanPropertyRowMapper<>(Article.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Article article){
        String sql="insert into Article (title,content,date) values (?,?, ?)";
        jt.update(sql,article.getTitle(),article.getContent(),article.getDate());
    }

    public void update(Article article) {
        String sql="update Article set title=?, content=? where id=?";
        jt.update(sql,article.getTitle(),article.getContent(),article.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Article WHERE id = ?";
        jt.update(sql, id);
    }


}
