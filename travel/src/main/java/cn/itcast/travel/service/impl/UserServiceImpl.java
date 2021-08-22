package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

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
        //2.1 设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2 设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3、激活邮件
        //3.1 邮件正文
        String content = "<a href = 'http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        //3.2 发送邮件
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //根据code 查询用户
        User user = userDao.findByCode(code);
        if(user != null){
            //修改用户的status状态
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }
}
