package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author wangxin
 * @create 2021/8/20 - 15:07
 */
public class UserDaoImpl implements UserDao {

    //成员变量
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserName(String username) {
        User user = null;
        try {
            //1、定义sql
            String sql = "select * from tab_user where username = ?";
            //2、执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        }catch (Exception e){

        }
        return user;
    }

    @Override
    public void save(User user) {
        //定义sql
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email) values (?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),
                user.getTelephone(),user.getEmail());
    }
}
