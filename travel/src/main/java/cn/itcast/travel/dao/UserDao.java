package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:08
 */
public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findByUserName(String username);


    /**
     * 保存用户
     * @param user
     */
    public void save(User user);

    /**
     * 根据code查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更新用户的status状态
     * @param user
     */
    void updateStatus(User user);
}
