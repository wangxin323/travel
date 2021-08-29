package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/27 - 20:08
 */
public class FavoriteDaoImpl implements FavoriteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid和uid查询搜藏信息
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    @Override
    public void add(String rid, int uid) {
        String sql = "insert into tab_favorite (rid,date,uid) values(?,?,?)";
        template.update(sql, rid,new Date(), uid);
    }

    /**
     * 查询用户收藏的总记录
     * @param uid
     * @return
     */
    @Override
    public int findCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where  uid = ?";
        Integer totalCount = template.queryForObject(sql, Integer.class, uid);
        return totalCount;
    }

    /**
     * 根据uid查询rid,并分页查询
     * @param uid
     * @return
     */
    @Override
    public List<Favorite> findRidByUid(int uid, int start, int pageSize) {
        String sql = "select * from tab_favorite where uid = ? limit ?, ?";
        List<Favorite> fList = template.query(sql, new BeanPropertyRowMapper<>(Favorite.class), uid, start, pageSize);
        return fList;
    }
}
