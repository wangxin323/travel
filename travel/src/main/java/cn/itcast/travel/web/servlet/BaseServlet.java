package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wangxin
 * @create 2021/8/24 - 16:29
 */
@WebServlet("/baseServlet")
public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("baseServlet中的service方法被执行了");
        //获取请求路径
        String uri = request.getRequestURI();
        System.out.println("请求路径uri"+uri);
        //获取方法名
        //字符串截取
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println("方法名"+methodName);
        //反射，获取方法对象
        //this, 谁调用我，我指向谁
        System.out.println(this);
        /**
         * 获取字节码 getClass()
         * 忽略访问权限 getDeclaredMethod
         */
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射
            //method.setAccessible(true);
            //执行方法
            //参数(对象，对象的参数)
            method.invoke(this, request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
