package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    /**
     * 分页查询
     * @param cid               分类id
     * @param currentPage       当前页码
     * @param pageSize          每页条数
     * @return
     */
    PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    /**
     * 根据传入的rid查询route对象
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
