package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategroyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author wangxin
 * @create 2021/8/24 - 18:45
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    //定义成员变量
    private CategoryService service = new CategroyServiceImpl();
    /**
     * 查询所有
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service完成查询
        List<Category> cs = service.findAll();
        //序列化为json
        //在BaseServlet 中封装的方法
        writrValueForJson(cs,response);
    }

}
