package com.zhzw.systemManage.servlet;

import com.alibaba.fastjson.JSONObject;
import com.siqiansoft.framework.bo.DatabaseBo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SelectRepeatCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseBo dbo = new DatabaseBo();
        //获取账号
        String code = request.getParameter("code");
        //查询该账号是否已存在
        String sql = "select pk from EAP_PERSON where CODE = '"+code+"'";
        try {
            List<HashMap<String,String>> list = dbo.prepareQuery(sql,null);
            String status = "";
            if(list.size()>0){
                status = "true";
            }else{
                status = "false";
            }
            JSONObject json = new JSONObject();
            json.put("status",status);
            response.getWriter().println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
