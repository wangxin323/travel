package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:06
 */
public class UserServiceImpl implements UserService {

    //dao 对象
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1、根据用户名查询用户对象
        User u = userDao.findByUserName(user.getUsername());
        //如果u为空则没有查询到
        if( u!= null){
            //用户已经存在
            return false;
        }
        //2、保存用户信息
        userDao.save(user);
        return true;
    }
}
