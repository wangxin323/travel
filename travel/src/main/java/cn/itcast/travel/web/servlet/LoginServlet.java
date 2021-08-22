package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

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
 * @create 2021/8/22 - 16:48
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        //设置登录失败时的响应信息
        ResultInfo info = new ResultInfo();

        //比较验证码
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);

        //将登录的用户信息存入session
        session.setAttribute("user", u);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
