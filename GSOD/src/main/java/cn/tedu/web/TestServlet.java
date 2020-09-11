package cn.tedu.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebServlet 可以用来指定通过什么路径（浏览器中输入的URI）访问当前的这个Servlet类
//通过value属性来指定，但是该属性名称可以省略，值必须以 / 开头，值的内容，在当前项目中必须是唯一的
@WebServlet(value = "/test1")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("前台亲求后台成功！");

        //response对象用来向前台返回数据
        response.getWriter().write("sssssssssssssssssssssss");
    }
}
