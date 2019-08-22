package com.zhzw.modularConfigure.servlet;


import com.alibaba.fastjson.JSON;
import com.zhzw.model.ZhzwChannelItemModel;
import com.zhzw.modularConfigure.service.modularConfigure;
import com.zhzw.modularConfigure.service.systemConfiguration;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

public class ModularConfigureServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        systemConfiguration sc = new systemConfiguration();
        modularConfigure mc = new modularConfigure();
        //获取ac值
        String ac = request.getParameter("ac");
        //获取列表页码数
        String page = request.getParameter("page");
        //获取模块状态
        String status = request.getParameter("status");//all:全部   0：已启用    1：未启用
        //获取条件搜索模块名称
        String systemName = request.getParameter("systemName");
        //获取频道页名称
        String channel = request.getParameter("channel");
        //获取模块名称
        String systemNameDetails = request.getParameter("details");
        //获取配置文件code
        String configCode = request.getParameter("configCode");
        //获取暂停应用频道页code
        String code = request.getParameter("code");
        //获取暂停应用模块code
        String systemCode = request.getParameter("system");
        //获取配置文件系统名称
        String systemConfigName = request.getParameter("systemConfigName");
        //获取配置文件logo
        String logo = request.getParameter("logo");
        //获取配置文件logoName
        String logoName = request.getParameter("logoName");
        //获取配置文件系统主题皮肤
        String subjectSkin = request.getParameter("subjectSkin");
        //获取配置文件版面类型
        String pageType = request.getParameter("pageType");
        //获取模块功能配置以及 对应的功能描述
        String allsystem = request.getParameter("allsystem");
        //获取基础模块配置类别
        String type = request.getParameter("type");
            try {
                JSONObject json = new JSONObject();
                if(ac.equals("selectList")){//列表所有数据查询
                    List<Object> list = mc.selectList(ac,status,systemName,page);
                    json.put("selectList",list);
                    response.getWriter().println(json);
                }else if(ac.equals("selectModularList")){//根据模块搜索查询
                    List<Object> list = mc.selectList(ac,status,systemName,page);
                    json.put("selectList",list);
                    response.getWriter().println(json);
                }else if(ac.equals("selectStatusList")){//根据状态搜索查询
                    List<Object> list = mc.selectList(ac,status,systemName,page);
                    json.put("selectList",list);
                    response.getWriter().println(json);
                }else if(ac.equals("selectExistModularList")){//根据频道页查询已有得模块
                    List<HashMap<String,String>> list = mc.selectModularList(channel);
                    json.put("selectExistModularList",list);
                    response.getWriter().println(json);
                }else if(ac.equals("selectModuleDetails")){//根据模块名称查询配置信息
                    List<Object> list = mc.selectModuleDetails(systemNameDetails);
                    json.put("selectModuleDetails",list);
                    response.getWriter().println(json);
                }else if(ac.equals("temporaryStorage")){// 暂存/配置且启动模块功能配置
                    ZhzwChannelItemModel zhzwChannelItemModel = JSON.parseObject(request.getParameter("Para"), ZhzwChannelItemModel.class);
                    HashMap<String,String> map = mc.temporaryStorage(zhzwChannelItemModel);
                    json.put("request",map);
                    response.getWriter().println(json);
                }else if(ac.equals("systemConfigList")){// 查询系统配置首页配置状态
                    List<HashMap<String,String>> list = sc.selectStatusList(configCode);
                    json.put("systemConfigList",list);
                    response.getWriter().println(json);
                }else if(ac.equals("stopapp")){// 暂停应用
                    mc.suspendApplicat(systemCode,code);
                }else if(ac.equals("preservatConfig")){// 保存系统配置
                    sc.preservatConfig(request,systemConfigName,logo,logoName,subjectSkin,pageType,configCode,type);
                }else if(ac.equals("preservatModularConfig")){// 保存模块功能配置
                    System.out.println("进入保存模块功能配置");
                    sc.preservatModularConfig(allsystem);
                    response.getWriter().println(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
