package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/27 - 20:07
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 添加记录
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 查询用户收藏的总记录
     * @param uid
     * @return
     */
    int findCountByUid(int uid);

    /**
     * 根据uid查询rid
     * @param uid
     * @return
     */
    List<Favorite> findRidByUid(int uid, int start, int pageSize);


}
