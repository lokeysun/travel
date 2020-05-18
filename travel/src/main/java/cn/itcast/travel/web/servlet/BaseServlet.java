package cn.itcast.travel.web.servlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法的分发
        //1.获取请求路径uri       请求uri：/travel/user/login
        String uri = req.getRequestURI();
        //2.获取方法名称          请求路径中的方法名称:login
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        //3.获取方法对象method，通过哪个servlet调用了我，然后通过反射再去调用他对应的方法
        //this=cn.itcast.travel.web.servlet.UserServlet@69d281db
        System.out.println(this);//谁调用了这个service方法，就代表谁

        //通过这个this，加上传入的路径中的方法名去获取这个方法名称对象
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //获取成功之后在重新调用这个方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json，并且写会客户端
     * @param obj
     * @param response
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {

        ObjectMapper mapper  = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化未json并返回
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
         return  mapper.writeValueAsString(obj);

    }
}
