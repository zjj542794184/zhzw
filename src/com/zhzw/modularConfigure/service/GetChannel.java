package com.zhzw.modularConfigure.service;

import com.siqiansoft.commons.FileIO;
import com.siqiansoft.commons.XmlSerializer;
import com.siqiansoft.framework.AppData;
import com.siqiansoft.framework.bo.DatabaseBo;
import com.siqiansoft.framework.model.LoginModel;
import com.zhzw.model.ChannelModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 获取配置文件数据
 */
public class GetChannel {
    DatabaseBo dbo = new DatabaseBo();
    /**
     * 根据角色获取管理端频道页
     */
    public List<HashMap<String,String>> getManageChannel(LoginModel login){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        //获取当前登录人code
        String userCode = login.getUserCode();
        String sql = "select MANAGETYPE from EAP_ACCOUNT where code = '"+userCode+"'";
        try {
            List<HashMap<String,String>> roleList = dbo.prepareQuery(sql,null);
            //获取管理类型
            String type = "";
            if(roleList.size()>0){
                type = roleList.get(0).get("MANAGETYPE");
            }
            String path = AppData.getInstance().getAppConfigDir()+"/manageChannel.xml";
            //读取xml
            ChannelModel[] channels = (ChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    for (ChannelModel channel : channels) {//循环所有得频道页
                        HashMap<String,String> map = new HashMap<String, String>();
                        //获取应用登录人类型
                        String channelType = channel.getType();
                        if(type.equals(channelType)){//当前登录人有该频道页
                            //获取name
                            String name = channel.getName();
                            map.put("name",name);
                            list.add(map);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
