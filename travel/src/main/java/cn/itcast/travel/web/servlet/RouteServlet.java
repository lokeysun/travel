package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public  void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //从客户端获取数据
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //接受rname，线路名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        //处理参数，将数据转换为int类型
        //类别id
        int cid =0 ;
        if(cidStr != null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //当前页码
        int currentPage =0 ;
        if(currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }
        //每页显示条数
        int pageSize =0 ;
        if(pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //调用service中的方法
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);

        //写回客户端
        writeValue(routePageBean,response);
    }


    /**
     * 根据传入的rid（线路id）查询旅游线路的详情信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数
        String rid = request.getParameter("rid");
        //调用service方法
        Route route = routeService.findOne(rid);
        //写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前用户是否收藏过这个线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取用户信息，判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){//没有登录
            uid = 0;
        }else{//用户已经登录
            uid = user.getUid();
        }
        //3.调用service方法,判断是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4.写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取用户id
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){//没有登录
           return ;
        }else{//用户已经登录
            uid = user.getUid();
        }
        //3.调用service方法
        favoriteService.add(rid,uid);

    }

}
