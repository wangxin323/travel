package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int findCountByUid(int uid) {
        return favoriteDao.findCountByUid(uid);
    }

    /**
     * 根据uid 查询rid,每一页的收藏路线
     * @param uid
     * @return
     */
    @Override
    public List<Integer> findRidByUid(int uid, int start, int pageSize) {
        List<Favorite> fList = favoriteDao.findRidByUid(uid,start,pageSize);
        List<Integer> ridList = new ArrayList<>();
        for (Favorite favorite : fList) {
            ridList.add(favorite.getRid());
        }
        // System.out.println(ridList);
        return ridList;

    }
}
