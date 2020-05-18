package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 分页查询
     * @param cid               分类id
     * @param currentPage       当前页码
     * @param pageSize          每页条数
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置总页数 = 总记录数 / 每页条数
        int totalPage =totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);

        //设置当前页面显示数据内容
        int start = (currentPage - 1)*pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置当前页码
        pb.setCurrentPage(currentPage);


        return pb;
    }

    /**
     * 根据id查询route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //查询出route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //查询出对应的图片信息,然后设置到route对象中
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);
        //查询出对应的seller对象,然后设置到route对象中
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //查询收藏次数,然后设置到route对象中
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
