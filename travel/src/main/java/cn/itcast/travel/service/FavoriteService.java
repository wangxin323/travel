package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/27 - 20:12
 */
public interface FavoriteService {
    /**
     * 查询收藏
     */
    public boolean findByRidandUid(String rid, int uid);

    /**
     * 添加收藏
     */
    void add(String rid, int uid);

    /**
     * 根据uid查询用户总收藏路线
     * @param uid
     * @return
     */
    public int findCountByUid(int uid);

    /**
     * 根据uid 查询rid，查询每一页的收藏路线
     * @param uid
     * @return
     */
    List<Integer> findRidByUid(int uid, int start, int pageSize);
}
