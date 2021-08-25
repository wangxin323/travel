package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import com.sun.tools.javadoc.Start;

import java.util.Collections;
import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:15
 */
public class RouteServiceImpl implements RouteService {
    // 创建Dao对象
    RouteDao routeDao = new RouteDaoImpl();

    /**
     * 分页查询
     * @param cid 类别id
     * @param currentPage 当前页码
     * @param pageSize  每页显示的条数
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //创建PageBean 对象
        PageBean pb = new PageBean();
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid);
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
        List<Route> list = routeDao.findByPage(cid, start, pageSize);
        pb.setList(list);

        return pb;
    }
}
