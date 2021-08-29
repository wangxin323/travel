package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:15
 */
public class RouteServiceImpl implements RouteService {
    // 创建Dao对象
    RouteDao routeDao = new RouteDaoImpl();

    RouteImgDao routeImgDao = new RouteImgDaoImpl();

    SellerDao sellerDao = new SellerDaoImpl();

    CategoryDao categoryDao = new CategoryDaoImpl();

    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 分页查询
     * @param cid 类别id
     * @param currentPage 当前页码
     * @param pageSize  每页显示的条数
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //创建PageBean 对象
        PageBean pb = new PageBean();
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);

        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        //设置当前页码
        pb.setCurrentPage(currentPage);

        //设置每页查询的条数
        pb.setPageSize(pageSize);

        //设置每页的数据
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);

        return pb;
    }

    /**
     * 查询一条记录
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //查询tab_route 表中的一条记录
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //从tab_route_img 表中查询图片
        List<RouteImg> list = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(list);
        //从tab_serller表中查询商家信息
        Seller seller = sellerDao.findByRid(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count = routeDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

    /**
     * 根据cid查询cname
     * @param cid
     * @return
     */
    public String findCname(int cid){
        Category category = categoryDao.findOne(cid);
        String cname = category.getCname();
        return cname;
    }

    /**
     * 查询人气旅游，count前limitCount名
     * @return
     */
    @Override
    public List<Route> findByCount(int limitCount) {
        return routeDao.findByCount(limitCount);
    }

    /**
     * 查询最新四条路线
     * @return
     */
    @Override
    public List<Route> findByRdate() {
        return routeDao.findByRdate();
    }

    /**
     * 随机查询四条
     * @return
     */
    @Override
    public List<Route> findTheme() {
        return routeDao.findTheme();
    }

    /**
     * 国内游
     * @return
     */
    @Override
    public List<Route> findGnByCid() {
        return routeDao.findGnByCid();
    }

    /**
     * 出境游
     * @return
     */
    @Override
    public List<Route> findCjByCid() {
        return routeDao.findCjByCid();
    }

    /**
     * 热门推荐，根据cid（分类名字）和count前六条
     * @return
     */
    @Override
    public List<Route> findHotByCidAndCount(int cid, int limitCount) {
        return routeDao.findHotByCidAndCount(cid, limitCount);
    }
    /**
     * 收藏排行榜分页查询
     * @param currentPage 当前页码
     * @param pageSize  每页展示的路线数
     * @param rname 路线名字
     * @param beginPrice    起步金额
     * @param endPrice  终止金额
     * @return
     */
    @Override
    public PageBean<Route> findCountByPage(int currentPage, int pageSize, String rname, int beginPrice, int endPrice) {
        //1、创建PageBean 对象
        PageBean pb = new PageBean();
        //设置总记录数
        int totalCount = routeDao.findTotalCount(rname, beginPrice, endPrice);
        pb.setTotalCount(totalCount);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        //2、设置当前页
        pb.setCurrentPage(currentPage);
        //3设置pageSize
        pb.setPageSize(pageSize);
        //4、获取每页显示数据
        //计算开始页
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findCountByPage(start, pageSize, rname, beginPrice, endPrice);
        pb.setList(list);

        return pb;
    }

    /**
     * 根据rid查询route路线
     * @param
     * @return
     */
    @Override
    public PageBean<Route> findByUid(int uid, int currentPage, int pageSize) {
        PageBean<Route> pb = new PageBean<>();

        List<Integer> ridList = new ArrayList<>();

        List<Route> routes = new ArrayList<>();

        //设置总数据
        int totalCount = favoriteDao.findCountByUid(uid);
        pb.setTotalCount(totalCount);

        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        //2、设置当前页
        pb.setCurrentPage(currentPage);
        //3设置pageSize
        pb.setPageSize(pageSize);


        //4、获取每页显示数据
        //计算开始页
        int start = (currentPage - 1) * pageSize;
        //从数据库中分页查询favorite集合
        List<Favorite> fList = favoriteDao.findRidByUid(uid, start, pageSize);
        //从favorite对象集集合中取出rid
        for (Favorite favorite : fList) {
            ridList.add(favorite.getRid());
        }
        //遍历rid集合，根据rid取出每一条路线放在routes集合中
        for (Integer rid : ridList) {
            Route one = routeDao.findOne(rid);
            routes.add(one);
        }
        //设置路线集合
        pb.setList(routes);

        return pb;
    }
}
