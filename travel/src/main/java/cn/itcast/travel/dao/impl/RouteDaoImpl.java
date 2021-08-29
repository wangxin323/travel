package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
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

    /**
     * 查询一条记录
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ?";
            //执行sql
            route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        } catch (DataAccessException e) {

        }
        return route;
    }

    /**
     * 根据rid 查询路线的收藏记录count
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "select count from tab_route where rid = ?";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }

    /**
     * 根据rid跟新路线的收藏记录count
     * @param rid
     */
    @Override
    public void updateCountByRid(int rid) {
        int count = findCountByRid(rid);
        String sql = "update tab_route set count = ? where rid = ?";
        count+=1;
        template.update(sql, count, rid);
    }

    /**
     * 查询count 最大的前limitCount条记录
     * @return
     */
    @Override
    public List<Route> findByCount(int limitCount) {
        //SQL 通过count降序排序，再取前四条
        String sql = "SELECT * FROM tab_route ORDER BY count DESC LIMIT ?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class),limitCount);
        return list;
    }

    /**
     * 根据时间查询最新四条记录
     * @return
     */
    @Override
    public List<Route> findByRdate() {
        //SQL 通过count降序排序，再取前四条
        String sql = "SELECT * FROM tab_route ORDER BY rdate DESC LIMIT 4";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /**
     * 根据时间查询最新四条记录
     * @return
     */
    @Override
    public List<Route> findTheme() {
        //随机查询四条
        String sql = "select * FROM tab_route WHERE rid>= ((SELECT MAX(rid) FROM tab_route) - (SELECT MIN(rid) " +
                "FROM tab_route)) * RAND()  LIMIT 4";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /**
     * 首页国内游
     * @return
     */
    @Override
    public List<Route> findGnByCid() {
        //国内游前6条数据
        String sql = "SELECT * FROM tab_route where cid = 5  ORDER BY rdate DESC LIMIT 6";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /**
     * 首页出境游
     * @return
     */
    @Override
    public List<Route> findCjByCid() {
        //国内游前6条数据
        String sql = "SELECT * FROM tab_route where cid = 4 ORDER BY rdate DESC LIMIT 6";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /**
     * 查询count 最大的前limitCount条记录
     * @return
     */
    @Override
    public List<Route> findHotByCidAndCount(int cid, int limitCount) {
        //SQL 通过count降序排序，再取前四条
        String sql = "SELECT * FROM tab_route where cid = ? ORDER BY count DESC LIMIT ?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class),cid,limitCount);
        return list;
    }
    /**
     * 收藏排行榜查询
     * @param start 开始位置
     * @param pageSize  每页查询条数 8
     * @param rname 线路名称
     * @param beginPrice 开始金额
     * @param endPrice 结束金额
     * @return
     */
    @Override
    public List<Route> findCountByPage(int start, int pageSize, String rname, int beginPrice,int endPrice) {
        //sql
        //SELECT * FROM tab_route where 1=1 AND rname LIKE "%长城%" AND price >= 3000 AND price <= 5000  ORDER BY count DESC  LIMIT 6,6
        //查询所有
        String sql = "SELECT * FROM tab_route where 1=1 ";
        //转成StringBuffer, 组合查询
        StringBuffer sb = new StringBuffer(sql);

        //定义一个参数数组
        List params = new ArrayList();

        //拼接路线名，模糊查询
        if(rname != null && rname.length() > 0){
            //拼接sql
            sb.append(" AND rname like ? ");
            //拼接参数
            params.add("%"+rname+"%");
        }
        //拼接金额
        if(beginPrice > 0 && endPrice > 0 && endPrice > beginPrice){
            sb.append(" AND price >= ? AND price <= ? ");
            params.add(beginPrice);
            params.add(endPrice);
        }

        sb.append(" ORDER BY count DESC LIMIT ?, ? ");
        params.add(start);
        params.add(pageSize);

        sql = sb.toString();

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());

        return list;
    }

    /**
     * 根据rname，金额查询记录数
     * @param rname
     * @param beginPrice
     * @param endPrice
     * @return
     */
    @Override
    public int findTotalCount(String rname, int beginPrice, int endPrice) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        //参数数组
        List params = new ArrayList();
        //拼接路线名，模糊查询
        if(rname != null && rname.length() > 0){
            //拼接sql
            sb.append(" AND rname like ? ");
            //拼接参数
            params.add("%"+rname+"%");
        }
        //拼接金额
        if(beginPrice > 0 && endPrice > 0 && endPrice > beginPrice){
            sb.append(" AND price >= ? AND price <= ? ");
            params.add(beginPrice);
            params.add(endPrice);
        }

        //将SQL转为string
        sql = sb.toString();

        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());
        return totalCount;
    }

}
