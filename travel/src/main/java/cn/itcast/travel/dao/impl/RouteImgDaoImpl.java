package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/27 - 10:02
 */
public class RouteImgDaoImpl implements RouteImgDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid查询图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        //定义sql
        String sql = "select * from tab_route_img where rid = ?";
        //执行sql，返回List
        List<RouteImg> list = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        return list;
    }
}
