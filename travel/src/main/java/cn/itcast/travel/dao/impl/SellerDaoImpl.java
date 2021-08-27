package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author wangxin
 * @create 2021/8/27 - 10:09
 */
public class SellerDaoImpl implements SellerDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询商家信息
     * @param sid
     * @return
     */
    @Override
    public Seller findByRid(int sid) {
        //定义sql
        String sql = "select * from tab_seller where sid = ?";
        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
}
