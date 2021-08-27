package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author wangxin
 * @create 2021/8/27 - 10:06
 * 商家信息
 */
public interface SellerDao {
    public Seller findByRid(int sid);
}
