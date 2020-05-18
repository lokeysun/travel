package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {

        /*//获取jedis
        Jedis jedis = JedisUtil.getJedis();

        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //判断查询出来的集合是否为空
        if(categorys ==null|| categorys.size()==0){
            System.out.println("从数据库中查询");
            //集合中没有内容就从数据库中查询
            cs = categoryDao.findAll();
            //查询出来之后需要重新放入到redis中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            System.out.println("从redis中查询");

            //redis中有数据，但是因为是set集合的，我们需要返回list集合，所以需要转换
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                cs.add(category);
            }
        }*/
        return categoryDao.findAll();
    }
}
