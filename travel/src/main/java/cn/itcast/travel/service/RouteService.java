package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:13
 * 路线service
 */
public interface RouteService {

    /**
     * 分页查询
     * @param cid 类别id
     * @param currentPage 当前页码
     * @param pageSize  每页显示的条数
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询一条记录
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 根据cid查询cname
     * @param cid
     * @return
     */
    public String findCname(int cid);

    /**
     * 根据count（收藏次数）查询前limitCount条
     * @return
     */
    public List<Route> findByCount(int limitCount);

    /**
     * 根据rdate查询前四条
     * @return
     */
    public List<Route> findByRdate();

    /**
     * 随机查询四条
     * @return
     */
    public List<Route> findTheme();

    /**
     * 国内游
     * @return 返回四条数据的集合
     */
    public List<Route> findGnByCid();

    /**
     * 处境游
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
     * @param currentPage 当前页码
     * @param pageSize  每页展示的路线数
     * @param rname 路线名字
     * @param beginPrice    起步金额
     * @param endPrice  终止金额
     * @return
     */
    public PageBean<Route> findCountByPage(int currentPage, int pageSize, String rname, int beginPrice,int endPrice);

    /**
     * 根据传递的rid集合查询路线
     * @param ridList
     * @return
     */
    public PageBean<Route> findByUid(int uid, int currentPage, int pageSize);
}
