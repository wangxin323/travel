package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:07
 */
public class UserDaoImpl implements UserDao {

    //成员变量
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Override
    public User findByUserName(String username) {
        User user = null;
        try {
            //1、定义sql
            String sql = "select * from tab_user where username = ?";
            //2、执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void save(User user) {
        //定义sql
        String sql =
                "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values " +
                        "(?,?,?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(),
                user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * 根据code查询用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where code = ?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新用户status
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        //定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        //执行sql
        template.update(sql, user.getUid());
    }

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUserNameAndPassword(String username, String password) {
        User user = null;
        try {
            //1、定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2、执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);

        } catch (Exception e) {

        }
        return user;
    }
}
