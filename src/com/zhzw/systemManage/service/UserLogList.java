package com.zhzw.systemManage.service;

import com.siqiansoft.framework.bo.DatabaseBo;
import com.siqiansoft.framework.model.PagingModel;
import com.siqiansoft.framework.model.db.ConditionModel;
import com.siqiansoft.framework.model.view.ControllerModel;
import com.siqiansoft.framework.model.view.ViewModel;
import com.siqiansoft.framework.util.PageControl;
import com.siqiansoft.platform.view.bo.ControllerBo;
import com.siqiansoft.platform.view.bo.ModuleBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 操作用户   用户操作日志
 */
public class UserLogList {
    DatabaseBo dbo = new DatabaseBo();

    public List<HashMap<String,String>> getList(ConditionModel[] cs, PagingModel page){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        ArrayList pagelist = new ArrayList();

        int from = page.getFrom();
        int to = page.getTo();
        int pageRows = page.getPageRows();
        int curPage = page.getCurPage();
        int i;
        String appsql = "select a.*,b.NAME from EAP_LOG a,EAP_ACCOUNT b where a.USERID = b.CODE and a.TYPE = 'APP' ";
        if (cs != null && !"".equals(cs)) {
            for (i = 0; i < cs.length; ++i) {
                if (!cs[i].getValue().equals((Object) null) && "" != cs[i].getValue()) {
                    if (cs[i].getId().equals("name")) {
                        appsql = appsql + " and b.NAME like'%" + cs[i].getValue() + "%'  order by  REQTIME desc";
                    }
                }
            }
        }else{
            appsql = appsql + " order by  REQTIME desc";
        }

        try {
            List<HashMap<String,String>> appList = dbo.prepareQuery(appsql,null);
            for (int j=0;j<appList.size();j++){
                HashMap<String,String> map = new HashMap<String, String>();
                //获取姓名
                String name = appList.get(j).get("NAME");
                //获取状态
                String status = appList.get(j).get("RESULTS");
                //获取操作时间
                String time = appList.get(j).get("REQTIME");
                //获取系统号
                String system = appList.get(j).get("SYSTEMID");
                //获取模块号
                String module = appList.get(j).get("MODULEID");
                //获取请求号
                String subtable = appList.get(j).get("ACTIONID");
                ControllerBo cb = new ControllerBo();
                ControllerModel controllerModel = cb.getController(system,module,subtable);
                //获取操作记录
                String actionid = controllerModel.getName();
                ModuleBo moduleBo = new ModuleBo();
                ViewModel viewModel = moduleBo.getModule(getConditionModel(system),module);
                String moduleid = viewModel.getName();
                map.put("name",name);
                map.put("moduleid",moduleid);
                map.put("actionid",actionid);
                map.put("results",status);
                map.put("reqtime",time);
                list.add(map);
            }

            if (list.size() == 0) {
                PageControl.calcPage(page, 0, 0);
                return null;
            } else {
                if (from == 1 && to == 0) {
                    to = list.size();
                }

                i = list.size();
                int pageCount = list.size() / pageRows + (list.size() % pageRows == 0 ? 0 : 1);
                page.setPageCount(pageCount);
                page.setRowsCount(i);
                if (to > list.size()) {
                    to = list.size();
                }

                page.setTo(to);
                return list.subList(from - 1, to);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public ConditionModel[] getConditionModel(String systemCode) {

        ConditionModel cmd = new ConditionModel();
        cmd.setType("LINK");
        cmd.setField("system");
        cmd.setValue(systemCode);
        ConditionModel[] cs = {cmd};
        return cs;
    }
}
