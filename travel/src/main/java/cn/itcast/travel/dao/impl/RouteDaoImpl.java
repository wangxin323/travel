package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
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
     * 组合查询
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        //定义sql
        // String sql = "select count(*) from tab_route where cid = ?";

        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        //参数数组
        List params = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ?");

            params.add(cid); // 添加?对应位置的参数
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        //将SQL转为string
        sql = sb.toString();

        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());
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
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        // String sql = "select * from tab_route where cid = ? limit ?, ?";

        String sql = "select * from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        //参数数组
        List params = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ?");

            params.add(cid); // 添加?对应位置的参数
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            //"%"引号内不能有空格，否则查询不出来
            params.add("%"+rname+"%");
        }

        sb.append(" limit ?, ?");
        //将SQL转为string
        sql = sb.toString();

        params.add(start);
        params.add(pageSize);

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return list;
    }
}
