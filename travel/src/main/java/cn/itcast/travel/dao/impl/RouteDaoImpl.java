package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:28
 */
public class RouteDaoImpl implements RouteDao {
    //连接数据库
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据id查询总记录数
     *
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid) {
        //定义sql
        String sql = "select count(*) from tab_route where cid = ?";
        Integer totalCount = template.queryForObject(sql, Integer.class, cid);
        return totalCount;
    }

    /**
     * 根据cid，start，pageSize 查询每页显示数据
     * @param cid 类别id
     * @param start 开始位置
     * @param pageSize 每页显示的数据条数
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ?, ?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, pageSize);
        return list;
    }
}
