package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:08
 */
public interface UserDao {

    public User findByUserName(String username);

    public void save(User user);
}
