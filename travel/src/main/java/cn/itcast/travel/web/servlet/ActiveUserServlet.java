package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangxin
 * @create 2021/8/22 - 15:13
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取激活码
        String code = request.getParameter("code");

        //判断code
        if(code != null){
            //2 调用service 完成激活
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);


            //判断标记
            String msg = null;
            if (flag){
                //激活成功
                msg = "激活成功，请<a href = 'login.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员";
            }
            //设置响应信息的格式
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
