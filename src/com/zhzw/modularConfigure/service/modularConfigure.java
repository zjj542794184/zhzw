package com.zhzw.modularConfigure.service;
import com.siqiansoft.commons.FileIO;
import com.siqiansoft.framework.AppData;
import com.siqiansoft.commons.XmlSerializer;
import com.zhzw.model.ZhzwChannelItemModel;
import com.zhzw.model.ZhzwChannelModel;
import com.zhzw.model.ZhzwItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 模块配置首页
 */
public class modularConfigure {
    String path = AppData.getInstance().getAppConfigDir()+"/zhzwchannel.xml";
    /**
     * 查询页面列表数据
     */
    public List<Object> selectList(String ac,String tjstatus,String tjsystemName,String page) throws Exception{
        List<Object> list = new ArrayList<Object>();

        //读取xml
        ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
        if(FileIO.existFile(path)) {//文件存在
            if(channels != null && channels.length >= 0) {//有频道页
                for (ZhzwChannelModel channel : channels) {//循环所有得频道页
                    //获取频道页名称
                    String name = channel.getName();
                    //获取频道页code
                    String code = channel.getCode();
                    //获取每个频道页下得模块对象
                    List<ZhzwChannelItemModel> modularChannel = channel.getItems();
                    if(modularChannel != null && modularChannel.size()>0){
                        for(int i=0;i<modularChannel.size();i++){
                            //获取模块名称
                            String systemName = modularChannel.get(i).getSystemName();
                            String system = modularChannel.get(i).getSystem();
                            //获取模块功能描述
                            String functionDescrip = modularChannel.get(i).getFunctionDescrip();
                            //获取模块状态
                            String status = modularChannel.get(i).getStatus();
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("system",system);
                            map.put("systemName",systemName);
                            map.put("name",name);
                            map.put("code",code);
                            map.put("functionDescrip",functionDescrip);
                            map.put("status",status);
                            if(ac.equals("selectModularList")){//根据模块名查询
                                if(tjstatus.equals("all")){
                                    if(systemName.contains(tjsystemName)){
                                        list.add(map);
                                    }
                                }else if(tjstatus.equals("0")){
                                    if(status.equals("0")){
                                        if(systemName.contains(tjsystemName)){
                                            list.add(map);
                                        }
                                    }
                                }else if(tjstatus.equals("1")){
                                    if(!status.equals("0")){
                                        if(systemName.contains(tjsystemName)){
                                            list.add(map);
                                        }
                                    }
                                }

                            }else if(ac.equals("selectStatusList")){//根据状态查询
                                if(tjstatus.equals("all")){//全部
                                    list.add(map);
                                }else if(tjstatus.equals("0")){//已启用
                                    if(status.equals("0")){
                                        list.add(map);
                                    }
                                }else if(tjstatus.equals("1")){//已启用
                                    if(!status.equals("0")){
                                        list.add(map);
                                    }
                                }
                            }else if(ac.equals("selectList")){//查询所有数据
                                list.add(map);
                            }

                        }
                    }
                }
            }
        }
        if(page == null || "".equals(page)){
            page = "1";
        }
        int pageCount = Integer.parseInt(page);
        //定义每页的长度
        int pageSize = 14;
        //定义当前页的第一位id
        int count = (pageCount - 1) * pageSize;
        //定义当前页的最后一位id
        int endCount = (pageCount - 1) * pageSize + pageSize;
        List<Object> returnList = new ArrayList<Object>();
        if(list.size()>0){
            for(int i=count;i<endCount;i++){
                if(i<list.size()){
                    returnList.add(list.get(i));
                }else{
                    break;
                }
            }
        }
        List<Object> returnList1 = new ArrayList<Object>();
        returnList1.add(returnList);
        returnList1.add(list.size());
        return returnList1;
    }

    /**
     * 根据频道页查询已配置模块
     */
    public List<HashMap<String,String>> selectModularList(String channel){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    for (ZhzwChannelModel ochannel : channels) {//循环所有得频道页
                        String ychannel = ochannel.getName();
                        if(ychannel.equals(channel)){
                            //获取每个频道页下得模块对象
                            List<ZhzwChannelItemModel> modularChannel = ochannel.getItems();
                            if(modularChannel != null && modularChannel.size()>0){
                                for(int i=0;i<modularChannel.size();i++){
                                    //获取模块名称
                                    String systemName = modularChannel.get(i).getSystemName();
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    map.put("systemName",systemName);
                                    list.add(map);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据模块名称查询配置信息
     */
    public List<Object> selectModuleDetails(String systemName){
        List<Object> list = new ArrayList<Object>();
        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    //定义频道页
                    String channelsStr = "";
                    for (ZhzwChannelModel ochannel : channels) {//循环所有得频道页
                        //定义频道页
                        String ychannelsStr = ochannel.getName();
                        //获取每个频道页下得模块对象
                        List<ZhzwChannelItemModel> modularChannel = ochannel.getItems();
                        if(modularChannel != null && modularChannel.size()>0){
                            for(int i=0;i<modularChannel.size();i++){
                                //获取模块名称
                                String ysystemName = modularChannel.get(i).getSystem();
                                //获取功能列表
                                List<ZhzwItemModel> itemModelList = modularChannel.get(i).getModuleList();
                                if(systemName.equals(ysystemName)){//该频道页下有该模块
                                    channelsStr = ychannelsStr + "," +channelsStr ;
                                    if(!list.contains(itemModelList)){
                                        list.add(itemModelList);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    list.add(channelsStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 暂存/配置且启动模块功能配置
     * button   0:配置并启用   1:暂存
     */
    public HashMap<String, String> temporaryStorage(ZhzwChannelItemModel zhzwChannelItemModel){
        List<ZhzwItemModel> zhzwItemModelList = zhzwChannelItemModel.getModuleList();
        String Stu = "1";
        String Msg = "请求成功";
        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            //获取频道页选择
            String channelPage = zhzwItemModelList.get(0).getChannelPage();
            String[] channelStr = channelPage.split(",");
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    for (ZhzwChannelModel ochannel : channels) {//循环所有得频道页
                        //获取频道页名称
                        String channelName = ochannel.getName();
                        List<ZhzwChannelItemModel> itemModelList = ochannel.getItems();
                        if(Arrays.asList(channelStr).contains(channelName)){//该频道页需添加该模块
                            if(itemModelList != null){//该频道页有该模块，替换
                                List list = new ArrayList();
                                for(int j=0;j<itemModelList.size();j++){
                                    list.add(itemModelList.get(j).getSystem());
                                    if(zhzwChannelItemModel.getSystem().equals(itemModelList.get(j).getSystem())){
                                        itemModelList.set(j,zhzwChannelItemModel);
                                        break;
                                    }
                                }
                                if(!list.contains(zhzwChannelItemModel.getSystem())){//存在替换
                                    itemModelList.add(zhzwChannelItemModel);
                                }
                            }else{//添加
                                System.out.println("进入添加wwwwwwwwwwwwwwwwwwww");
                                List<ZhzwChannelItemModel> newitemModelList = new ArrayList<ZhzwChannelItemModel>();
                                newitemModelList.add(zhzwChannelItemModel);
                                ochannel.setItems(newitemModelList);
                            }
                        }else{//该频道页不需要该模块
                            if(itemModelList != null){
                                for(int j=0;j<itemModelList.size();j++){
                                    //获取原本xml模块code
                                    String system = itemModelList.get(j).getSystem();
                                    //获取前台传来得模块code
                                    String paraSystem = zhzwChannelItemModel.getSystem();
                                    if(system.equals(paraSystem)){
                                        itemModelList.remove(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            XmlSerializer.getInstance().toXMLFile(channels, path);
        } catch (Exception e) {
            Stu = "0";
            Msg = "请求失败";
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("Stu",Stu);
        map.put("Msg",Msg);
        return map;
    }

    /**
     * 暂停应用
     *  system:模块code
     *  code:频道code
     */
    public void suspendApplicat(String system,String code){

        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    for (ZhzwChannelModel ochannel : channels) {//循环所有得频道页
                        //获取频道页code
                        String pdCode = ochannel.getCode();
                        if(pdCode.equals(code)){//该频道页与要暂停的频道页相等
                            //获取频道页的功能
                            List<ZhzwChannelItemModel> itemModelList = ochannel.getItems();
                            for(int i=0;i<itemModelList.size();i++){
                                //获取模块code
                                String systemCode = itemModelList.get(i).getSystem();
                                if(systemCode.equals(system)){//要暂停的模块在该频道页下，则修改该模块状态为暂停
                                    itemModelList.get(i).setStatus("3");
                                }
                            }
                        }
                    }
                }
            }
            XmlSerializer.getInstance().toXMLFile(channels, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
