package com.zhzw.modularConfigure.service;

import com.siqiansoft.commons.FileIO;
import com.siqiansoft.commons.XmlSerializer;
import com.siqiansoft.framework.AppData;
import com.siqiansoft.framework.model.LoginModel;
import com.siqiansoft.framework.model.db.ConditionModel;
import com.zhzw.model.SystemConfigModel;
import com.zhzw.model.ZhzwChannelItemModel;
import com.zhzw.model.ZhzwChannelModel;
import com.zhzw.systemManage.service.AdministratorManage;
import sun.misc.BASE64Decoder;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 系统配置模块
 */
public class systemConfiguration {
    String path = AppData.getInstance().getAppConfigDir()+"/zhzwSystemConfig.xml";
    AdministratorManage am = new AdministratorManage();
    /**
     * 查询系统配置首页配置与详情
     */
    public List<HashMap<String,String>> selectStatusList(String systemConfigCode){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //读取xml
            SystemConfigModel[] systemConfigs = (SystemConfigModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if (systemConfigs != null && systemConfigs.length >= 0) {//有频道页
                    for (SystemConfigModel systemConfig : systemConfigs) {//循环所有得频道页
                        //获取name
                        String name = systemConfig.getName();
                        //获取code
                        String code = systemConfig.getCode();
                        //获取是否配置状态
                        String status = systemConfig.getStatus();
                        //获取系统名称
                        String systemName = systemConfig.getSystemName();
                        //获取系统logo
                        String logo = systemConfig.getLogo();
                        //获取系统主题皮肤
                        String subjectSkin = systemConfig.getSubjectSkin();
                        //获取版面类型
                        String PageType = systemConfig.getPageType();
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("name",name);
                        map.put("code",code);
                        map.put("status",status);
                        if(systemConfigCode != null && !"".equals(systemConfigCode)){
                            if(code.equals(systemConfigCode)){
                                map.put("systemName",systemName);
                                map.put("logo",logo);
                                map.put("subjectSkin",subjectSkin);
                                map.put("PageType",PageType);
                                list.add(map);
                                break;
                            }
                        }else{
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

    /**
     * 保存系统配置
     * systemName:系统名称  subjectSkin：系统主题皮肤  PageType：版面类型  code:系统配置code
     */
    public void  preservatConfig(LoginModel login,HttpServletRequest request, String systemName, String logo, String logoName, String subjectSkin, String PageType, String code, String ttype){
        String operation = "";
        try {
            String logoPath = "";
            if(code.equals("jichu")){
                String name1 = logoName.replace(".", ",");
                String strs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                StringBuffer buff = new StringBuffer();
                for(int i=1;i<=3;i++){
                    char str = strs.charAt((int)(Math.random() * 26));
                    buff.append(str);
                }
                logo = logo.split(",")[1];
                //解码前来的64照片
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] decoding;
                OutputStream out =null;
                decoding = decoder.decodeBuffer(logo);
                for (int i = 0; i < decoding.length; ++i) {
                    if(decoding[i] < 0) {
                        decoding[i] += 256;
                    }
                }
                Calendar now = Calendar.getInstance();
                String fileName = buff.toString()+now.get(Calendar.YEAR)+((now.get(Calendar.MONTH) + 1)+"") +now.get(Calendar.DAY_OF_MONTH)
                        +now.get(Calendar.HOUR_OF_DAY)+now.get(Calendar.MINUTE)+now.get(Calendar.MINUTE)+System.currentTimeMillis();
                String type = name1.split(",")[1];
                File file = new File(request.getSession().getServletContext().getRealPath("/")+"logo/"+fileName+"."+type);
                if(!file.exists()){
                    file.createNewFile();
                }
                out = new FileOutputStream(file.getCanonicalFile());
                out.write(decoding);
                out.flush();
                String tpath = request.getSession().getServletContext().getRealPath("/");
                String str[] = tpath.substring(0,tpath.length()-1).split("/");
                String lj = str[str.length-1].replace(".",",");
                logoPath = lj.split(",")[0]+"/logo/"+fileName+"."+type;
            }


            //读取xml
            SystemConfigModel[] systemConfigs = (SystemConfigModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if (systemConfigs != null && systemConfigs.length >= 0) {//有频道页
                    for (SystemConfigModel systemConfig : systemConfigs) {//循环所有得频道页
                        //获取code
                        String sccode = systemConfig.getCode();
                        if(sccode.equals(code)){
                            if(code.equals("jichu")){//设置基础信息配置
                                systemConfig.setSystemName(systemName);
                                systemConfig.setStatus("1");
                                systemConfig.setPageType(ttype);
                                systemConfig.setLogo(logoPath);
                                operation = login.getUserName()+"修改了基础信息配置";
                                break;
                            }else if(code.equals("gaoji")){//设置高级信息配置
                                systemConfig.setSubjectSkin(subjectSkin);
                                systemConfig.setStatus("1");
                                systemConfig.setPageType(PageType);
                                operation = login.getUserName()+"修改了高级信息配置";
                                break;
                            }
                        }
                    }
                }
            }
            XmlSerializer.getInstance().toXMLFile(systemConfigs, path);

            //添加日志信息
            am.addLog(login,operation,"SUCCESS");
        } catch (Exception e) {
            //添加日志信息
            am.addLog(login,operation,"fail");
            e.printStackTrace();
        }
    }

    /**
     * 查询模块功能信息
     */
    public List<HashMap<String,String>> selectModular(ConditionModel[] cs){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        String mpath = AppData.getInstance().getAppConfigDir()+"/zhzwchannel.xml";
        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(mpath);
            //定义查询条件的模块名称
            String smodularName = "";
            if (cs != null && !"".equals(cs)) {
                for (int i = 0; i < cs.length; ++i) {
                    if (!cs[i].getValue().equals((Object) null) && "" != cs[i].getValue()) {
                        if (cs[i].getId().equals("systemname")) {
                            smodularName = cs[i].getValue();
                        }
                    }
                }
            }
            if(FileIO.existFile(path)) {//文件存在
                if(channels != null && channels.length >= 0) {//有频道页
                    for (ZhzwChannelModel channel : channels) {//循环所有得频道页
                        //获取该频道页下的模块集合
                        List<ZhzwChannelItemModel> modularChannel = channel.getItems();
                        if(modularChannel != null && modularChannel.size()>0){
                            for(int i=0;i<modularChannel.size();i++){
                                //获取模块名称
                                String systemName = modularChannel.get(i).getSystemName();
                                String system = modularChannel.get(i).getSystem();
                                //获取模块功能描述
                                String functionDescrip = modularChannel.get(i).getFunctionDescrip();
                                HashMap<String,String> map = new HashMap<String, String>();
                                map.put("system",system);
                                map.put("systemName",systemName);
                                map.put("functionDescrip",functionDescrip);
                                if(!"".equals(smodularName) && smodularName != null){//有查询条件
                                    if(systemName.contains(smodularName)){
                                        if(!list.contains(map)){//不包含则进行添加
                                            list.add(map);
                                        }
                                    }
                                }else{//查询所有模块
                                    if(!list.contains(map)){//不包含则进行添加
                                        list.add(map);
                                    }
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
     * 保存模块功能配置
     * allsystem:模块号对应的功能描述
     */
    public void preservatModularConfig(LoginModel login,String allsystem){
        String mpath = AppData.getInstance().getAppConfigDir()+"/zhzwchannel.xml";
        String[] str = allsystem.split(";");
        try {
            //读取xml
            ZhzwChannelModel[] channels = (ZhzwChannelModel[]) XmlSerializer.getInstance().fromXMLFile(mpath);
            if(FileIO.existFile(path)) {//文件存在
                if (channels != null && channels.length >= 0) {//有频道页
                    for (ZhzwChannelModel channel : channels) {//循环所有得频道页
                        //获取该频道页下的模块集合
                        List<ZhzwChannelItemModel> modularChannel = channel.getItems();
                        if(modularChannel != null && modularChannel.size()>0){
                            for(int i=0;i<modularChannel.size();i++){
                                //获取模块code
                                String system = modularChannel.get(i).getSystem();
                                for(int j=0;j<str.length;j++){
                                    String systemCode = str[j].split(":")[0];
                                    System.out.println("获取频道页下模块code为："+system);
                                    System.out.println("获取保存模块code为："+systemCode);
                                    if(systemCode.equals(system)){
                                        System.out.println("获取保存模块描述为："+str[j].split(":")[1]);
                                        modularChannel.get(i).setFunctionDescrip(str[j].split(":")[1]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //读取xml
            SystemConfigModel[] systemConfigs = (SystemConfigModel[]) XmlSerializer.getInstance().fromXMLFile(path);
            if(FileIO.existFile(path)) {//文件存在
                if (systemConfigs != null && systemConfigs.length >= 0) {//有频道页
                    for (SystemConfigModel systemConfig : systemConfigs) {//循环所有得频道页
                        //获取code
                        String sccode = systemConfig.getCode();
                        if(sccode.equals("gongneng")){
                            systemConfig.setStatus("1");
                        }
                    }
                }
            }
            XmlSerializer.getInstance().toXMLFile(systemConfigs, path);
            XmlSerializer.getInstance().toXMLFile(channels, mpath);
            //添加日志信息
           am.addLog(login,login.getUserName()+"修改了模块功能描述","SUCCESS");
        } catch (Exception e) {
            //添加日志信息
            am.addLog(login,login.getUserName()+"修改了模块功能描述","fail");
            e.printStackTrace();
        }


    }
}
