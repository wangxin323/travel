package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wangxin
 * @create 2021/8/24 - 18:43
 */
public class CategroyServiceImpl implements CategoryService {

    //成员变量，
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1 从redis中查询数据
        //1.1 创建jedis对象
        Jedis jedis = JedisUtil.getJedis();
        //1.2 使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3 查询 sortedset 中的分数（cid） 和 值（cname）
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2 判断categorys是否为空
        if(categorys == null || categorys.size() ==0){
            // System.out.println("从数据库中查询....");
            //如果为空，则从数据库中查询
            cs = categoryDao.findAll();
            //将查询的结果存入jedis
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid() ,cs.get(i).getCname());
            }
        }else {
            // System.out.println("从redis中查询....");
            //如果不为空，将sortset中的数据存list
            cs = new ArrayList<>();
            //遍历categorys
            for (Tuple tuple : categorys) {
                //创建Category对象
                Category category = new Category();
                //获取tuple中的值
                category.setCname(tuple.getElement());
                //获取tuple中的分数
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
