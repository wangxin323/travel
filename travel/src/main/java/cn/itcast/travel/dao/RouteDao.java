package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
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

    /**
     * 查询count最大的前limitCount条数据
     * @return 返回四条数据的集合
     */
    public List<Route> findByCount(int limitCount);

    /**
     * 根据添加时间查询最新添加的记录
     * @return 返回四条数据的集合
     */
    public List<Route> findByRdate();

    /**
     * 随机查询四条 主体旅游
     * @return 返回四条数据的集合
     */
    public List<Route> findTheme();

    /**
     * 国内游
     * @return 返回四条数据的集合
     */
    public List<Route> findGnByCid();

    /**
     * 国内游
     * @return 返回四条数据的集合
     */
    public List<Route> findCjByCid();

    /**
     * 热门推荐
     * @param cid
     * @param limitCount
     * @return
     */
    public List<Route> findHotByCidAndCount(int cid, int limitCount);

    /**
     * 收藏排行榜分页查询
     * @param start 开始位置
     * @param pageSize  每页查询条数 8
     * @param rname 线路名称
     * @param beginPrice 开始金额
     * @param endPrice 结束金额
     * @return
     */
    public List<Route> findCountByPage(int start, int pageSize, String rname, int beginPrice,int endPrice);

    /**
     * 根据rname，金额查询记录数
     * @param rname
     * @param rname
     * @param rname
     * @return
     */
    public int findTotalCount(String rname, int beginPrice,int endPrice);

}
