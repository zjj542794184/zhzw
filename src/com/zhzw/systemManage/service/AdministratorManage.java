package com.zhzw.systemManage.service;
import cn.org.bjca.mssp.msspjce.pqc.math.linearalgebra.ByteUtils;
import com.siqiansoft.framework.bo.DatabaseBo;
import com.siqiansoft.framework.model.LoginModel;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 系统管理的管理员管理模块
 */
public class AdministratorManage {
    DatabaseBo dbo = new DatabaseBo();
    //获取当前时间
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String nowTime = df.format(new Date());
    /**
     * 新增管理员
     */
    public void addAdministrator(HashMap<String,String> map,LoginModel login){
        //获取密码
        String pwd = map.get("pwd");
        try {
            String passWord = encryption(pwd);
            map.put("PWD",passWord);
            map.put("TYPE","E");
            map.put("ORGCODE","root");
            map.put("STATUS","A");
            //查询最大sn
            String maxSql = "select max(sn) sn from EAP_PERSON";
            List<HashMap<String,String>> maxList = dbo.prepareQuery(maxSql,null);

            String maxSn = maxList.get(0).get("SN");
            map.put("SN",(Integer.parseInt(maxSn)+1)+"");//顺序号
            map.put("CREATETIME",nowTime);//创建时间
            //向EAP_PERSON添加数据
            dbo.insert(map,"EAP_PERSON");
            //向EAP_ACCOUNT添加数据
            //查询最大sn
            String maxAccountSql = "select max(sn) sn from EAP_ACCOUNT";
            List<HashMap<String,String>> maxAccountList = dbo.prepareQuery(maxAccountSql,null);
            String maxAccountSn = maxAccountList.get(0).get("SN");
            HashMap accountMap = new HashMap();
            accountMap.put("ORGCODE","root");//组织号
            accountMap.put("DEPTCODE","root");//部门号
            accountMap.put("CODE",map.get("code"));//编码
            accountMap.put("NAME",map.get("name"));//名称
            accountMap.put("TIMESLICE","timeslice1");//时间戳
            accountMap.put("RANK","2");//职务
            accountMap.put("STATUS","A");//STATUS
            accountMap.put("SN",(Integer.parseInt(maxAccountSn)+1)+"");//SN
            accountMap.put("PERSONCODE",map.get("code"));//人员流水号
            accountMap.put("MANAGETYPE",map.get("managetype"));//管理类型
            dbo.insert(accountMap,"EAP_ACCOUNT");

            //添加日志信息
            addLog(login,"新增了一个管理账号："+map.get("code"),"SUCCESS");
        } catch (Exception e) {
            //添加日志信息
            addLog(login,"新增了一个管理账号："+map.get("code"),"fail");
            e.printStackTrace();
        }

    }
    /**
     * 修改管理员信息
     */
    public void updateAdministrator(HashMap<String,String> map,LoginModel login){
        //获取密码
        String pwd = map.get("pwd");
        //进行加密
        String passWord =encryption(pwd);
        map.put("PWD",passWord);
        try {
            //修改EAP_PERSON数据
            dbo.update(map,"EAP_PERSON");
            //修改EAP_ACCOUNT数据
            //根据code查询PK值
            String pkSql = "select pk from EAP_ACCOUNT where code = '"+map.get("code")+"'";
            List<HashMap<String,String>> pkList = dbo.prepareQuery(pkSql,null);
            String pk = pkList.get(0).get("PK");
            HashMap accountMap = new HashMap();
            accountMap.put("pk",pk);//pk
            accountMap.put("NAME",map.get("name"));//名称
            dbo.update(accountMap,"EAP_ACCOUNT");

            //添加日志信息
            addLog(login,"修改了一个管理账号："+map.get("code"),"SUCCESS");
        } catch (Exception e) {
            //添加日志信息
            addLog(login,"修改了一个管理账号："+map.get("code"),"fail");
            e.printStackTrace();
        }

    }

    /**
     * 删除管理员账号
     */
    public void delete(LoginModel login,String pk,String code){
        String sql = "update EAP_PERSON set STATUS = 'D' where pk = '"+pk+"'";
        try {
            //dbo.prepareUpdate(sql,null);
            String accSql = "update EAP_ACCOUNT set STATUS = 'D' where code = '"+code+"'";
            dbo.prepareUpdate(accSql,null);
            //添加日志信息
            addLog(login,"删除了一个管理账号："+code,"SUCCESS");
        } catch (Exception e) {
            //添加日志信息
            addLog(login,"删除了一个管理账号："+code,"fail");
            e.printStackTrace();
        }
    }

    /**
     * 对密码进行md5加密
     */
    public String encryption(String pwd){
        String passWord = "";
        try {
            //给密码进行md5加密
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(pwd.getBytes("utf-8"));
            //加密后的密码
            passWord = ByteUtils.toHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passWord;
    }

    /**
     * 添加日志 信息
     * operation : 操作项
     * status ：操作状态
     */
    public void addLog(LoginModel login,String operation, String status){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String nowTime = df.format(new Date());
        String userCode = login.getUserCode();
        //查询当前登录人管理类型
        String sql = "select MANAGETYPE from EAP_PERSON where code = '"+userCode+"'";
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        String type = "";
        if(list.size()>0){
            type = list.get(0).get("MANAGETYPE");
        }
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("CODE",login.getUserCode());
        map.put("NAME",login.getUserName());
        map.put("TYPE",type);
        map.put("STATUS",status);
        map.put("OPERATION",operation);
        map.put("CREATETIME",nowTime);
        try {
            dbo.insert(map,"EAP_SYSTEM_LOG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
