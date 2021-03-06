package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/24 - 18:40
 */
public class CategoryDaoImpl implements CategoryDao {
    //成员变量
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有种类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //定义sql
        String sql = "Select * from tab_category";
        //执行sql
        List<Category> cs = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return cs;
    }

    /**
     * 根据cid查询一条信息
     * @param cid
     * @return
     */
    @Override
    public Category findOne(int cid) {
        String sql = "select * from tab_category where cid = ?";
        //执行sql
        Category category = template.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), cid);
        return category;
    }
}
