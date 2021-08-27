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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:01
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    //service对象
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 从客户端获取参数
        String currentPageStr = request.getParameter("currentPage"); //当前页码
        String pageSizeStr = request.getParameter("pageSize"); //每页显示的条数
        String cidStr = request.getParameter("cid");  //类别id
        
        //获取用户输入的rname，路线名称
        String rname = request.getParameter("rname");
        //解决tomcat中文乱码问题
        if(rname != null){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }

        //2 处理参数
        int cid = 0; //类别id
        if(cidStr != null && cidStr.length() >0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }

        int pageSize = 0;  //每页显示的条数
        if(pageSizeStr != null && pageSizeStr.length() >0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }

        int currentPage = 0;    //当前页码
        if(currentPageStr != null && currentPageStr.length() >0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        //3 调用service 完成查询
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //4 序列化数据为json
        writrValueForJson(routePageBean, response);
    }

    /**
     * 查询一条记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        //获取rid
        String rid = request.getParameter("rid");
        if(rid == null || rid.length() == 0){
            rid = "1";
        }
        //调用service完成查询
        Route route = routeService.findOne(rid);

        //根据cid查询cname
        String cname = routeService.findCname(route.getCid());

        //拼接对象route 和 cname
        HashMap<String,Object> map = new HashMap<>();
        map.put("route", route);
        map.put("cname", cname);
        //将route对象序列化为json
        // writrValueForJson(route, response);
        // writrValueForJson(list, response);
        writrValueForJson(map, response);
    }

    /**
     * 判断用户是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前端获取rid
        String rid = request.getParameter("rid");

        //从session中获取user对象
        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if(user == null){
            //用户没有登录
            uid = 0;
        }else {
            //用户已经登录
            uid = user.getUid();
        }

        boolean flag = favoriteService.findByRidandUid(rid, uid);

        //序列化为json，回写给客户点
        writrValueForJson(flag, response);
    }

    /**
     * 判断用户是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前端获取rid
        String rid = request.getParameter("rid");

        //从session中获取user对象
        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if(user == null){
            //用户没有登录
            return;
        }else {
            //用户已经登录
            uid = user.getUid();
        }

        favoriteService.add(rid, uid);

    }
}
