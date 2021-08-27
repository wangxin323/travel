package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @author wangxin
 * @create 2021/8/27 - 20:13
 */
public class FavoriteServiceImpl implements FavoriteService {

    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean findByRidandUid(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null; // favorite为空返回false，不为空返回true
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        // 更新tabe_route中的count
        routeDao.updateCountByRid(Integer.parseInt(rid));
        // 向tab_favorite添加记录
        favoriteDao.add(rid,uid);
    }
}
