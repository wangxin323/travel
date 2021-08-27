package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/27 - 10:00
 */
public interface RouteImgDao {
    /**
     * 根据rid查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
