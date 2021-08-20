package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:06
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);
}
