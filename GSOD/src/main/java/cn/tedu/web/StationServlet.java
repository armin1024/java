package cn.tedu.web;

import cn.tedu.hbase.HBaseUtils01;
import cn.tedu.pojo.Stations;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StationServlet", value = "/stations")
public class StationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取Context对象
        ServletContext context = this.getServletContext();
        //在context对象中，存储对象是，使用k-v键值对的形式存储(stations, list)
        if (context.getAttribute("stations") == null){
            List<Stations> list = HBaseUtils01.getDatas();

            //在前后台传递数据的时候，通常都是以JSON字符串的形式进行传递
            //将java对象转变为JSON字符串
            String jsonStr = JSON.toJSONString(list);

            //将该字符串缓存起来，以便之后的调用
            context.setAttribute("stations", jsonStr);
            response.getWriter().write(jsonStr);
        }else { //在context对象中，已经有了数据，直接从该对象中获取即可
            //将数据通过response对象返回
            response.getWriter().write(context.getAttribute("stations").toString());
        }
    }
}
