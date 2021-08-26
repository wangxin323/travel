package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:27
 */
public interface RouteDao {
    public int findTotalCount(int cid,String rname);

    public List<Route> findByPage(int cid, int start, int pageSize, String rname);
}
