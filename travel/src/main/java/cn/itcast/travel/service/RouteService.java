package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

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
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
}