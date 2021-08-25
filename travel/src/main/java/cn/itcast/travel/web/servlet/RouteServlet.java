package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangxin
 * @create 2021/8/25 - 16:01
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    //service对象
    private RouteService routeService = new RouteServiceImpl();
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
        System.out.println(currentPageStr);
        System.out.println(pageSizeStr);
        System.out.println(cidStr);

        //2 处理参数
        int cid = 0; //类别id
        if(cidStr != null && cidStr.length() >0){
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
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize);
        //4 序列化数据为json
        writrValueForJson(routePageBean, response);
    }
}
