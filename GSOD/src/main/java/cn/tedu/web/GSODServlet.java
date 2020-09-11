package cn.tedu.web;

import cn.tedu.hbase.HBaseUtils02;
import cn.tedu.pojo.GSOD;
import cn.tedu.pojo.GSOD01;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GSODServlet", value = "/gsod")
public class GSODServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取Context对象
        ServletContext context = this.getServletContext();
        //在context对象中，存储对象是，使用k-v键值对的形式存储(stations, list)

        List<GSOD> list = HBaseUtils02.getDatas();

        //在前后台传递数据的时候，通常都是以JSON字符串的形式进行传递
        //将java对象转变为JSON字符串
        String jsonStr = JSON.toJSONString(list);

        //将该字符串缓存起来，以便之后的调用
        context.setAttribute("gsod", jsonStr);
        response.getWriter().write(jsonStr);
        System.out.println(jsonStr);
    }
}
