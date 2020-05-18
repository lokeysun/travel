package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 根据客户端传递过来的数据
     * @param cid       分类id
     * @param start      从第几页开始的
     * @param pageSize    当前页显示的数据量
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);


    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
