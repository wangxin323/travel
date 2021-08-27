package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;

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
}
