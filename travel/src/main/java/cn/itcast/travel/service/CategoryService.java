package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/24 - 18:43
 */
public interface CategoryService {
    /**
     * 查询所有种类
     */
    public List<Category> findAll();
}
