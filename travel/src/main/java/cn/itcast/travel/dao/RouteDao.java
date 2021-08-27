package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:27
 */
public interface RouteDao {
    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 查询一页显示的数据
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 查询 一条记录
     * @param rid
     * @return
     */
    public Route findOne(int rid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 根据rid更新count
     * @param rid
     */
    void updateCountByRid(int rid);
}
