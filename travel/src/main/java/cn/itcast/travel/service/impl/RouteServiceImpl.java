package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

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

    public String findCname(int cid){
        Category category = categoryDao.findOne(cid);
        String cname = category.getCname();
        return cname;
    }
}
