package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author wangxin
 * @create 2021/8/24 - 16:28
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //成员变量(声明业务对象)
    private UserService service = new UserServiceImpl();
    //设置登录失败时的响应信息
    private ResultInfo info = new ResultInfo();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        //验证码
        //获取用户输入的验证码
        String check = request.getParameter("check");
        //从session缓存中获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能用一次
        session.removeAttribute("CHECKCODE_SERVER");

        //比较验证码
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误，给出提示信息
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info序列化为json数据
            String json = writrValueAsStringForJson(info, response);

            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        //1、获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2、封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3、调用service，完成注册
        boolean flag = service.regist(user);

        //4、响应结果
        if(flag){
            //注册成功
            info.setFlag(true);

        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名已经存在!");
        }


        //将info序列化为json数据
        String json = writrValueAsStringForJson(info, response);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户输入的验证码
        String check = request.getParameter("check");
        //从缓存中获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能用一次
        session.removeAttribute("CHECKCODE_SERVER");


        //获取用户输入的信息（用户名和密码）
        Map<String, String[]> map = request.getParameterMap();
        //创建User对象
        User user = new User();
        //封装user对象
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service查询
        User u = service.login(user);

        //比较验证码,响应信息
        if(check != null && check.equalsIgnoreCase(checkcode_server)){
            //验证码通过
            //判断service 查询的user（u） 是否为空
            if(u ==  null){
                // 用户名或密码错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }
            //判断用户是否用邮箱激活
            if(u != null && !u.getStatus().equalsIgnoreCase("Y")){
                //没有激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活，请用邮箱激活");
            }
            //登录成功
            if(u != null && u.getStatus().equalsIgnoreCase("Y")){
                info.setFlag(true);
            }
        }else{
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }

        //设置编码格式
        response.setContentType("application/json;charset=utf-8");
        //响应数据，序列化为json
        writrValueForJson(info, response);

        //将登录的用户信息存入session
        session.setAttribute("user", u);
    }

    /**
     * 查找单个用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户的用户名 替换 欢迎回来，admin
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        //将user 返回给客户端
        //系列化为json
        ObjectMapper mapper = new ObjectMapper();
        //设置编码
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }
    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、销毁session
        request.getSession().invalidate();

        //2、跳转页面（重定向）
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取激活码
        String code = request.getParameter("code");

        //判断code
        if(code != null){
            //2 调用service 完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);


            //判断标记
            String msg = null;
            if (flag){
                //激活成功
                msg = "激活成功，请<a href ='"+request.getContextPath()+"/index.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员";
            }
            //设置响应信息的格式
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
