package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/24 - 18:38
 */
public interface CategoryDao {
    /**
     * 查询所有种类
     */
    public List<Category> findAll();

    public Category findOne(int cid);
}
