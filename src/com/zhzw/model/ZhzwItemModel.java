package com.zhzw.model;

import java.util.List;

/**
 * @desc 模块功能设置
 * @time 2019年6月17日
 * @author liujianghua
 */

        public class ZhzwItemModel {

                private String channelPage; //频道页选择
                private String news; //消息选择
                private String optionalHusks; //可选模块
                private String optionalFunction; //可选功能
                private List<LeaveType> LeaveTypeList;  //请假类型及流程方式
                private String calculation;//年假计算方式
                private String dutyImport;//值班导入方式
                private String dutySignType;//值班签到类型
                private String shiftMode;//换班方式
                private String reminder;//红灯提醒
                private String defaultTime;//默认时间
                private String processSelect;//流程选择

        public ZhzwItemModel() {
                super();
        }

        public ZhzwItemModel(String channelPage, String news, String optionalHusks, List<LeaveType> LeaveTypeList, String calculation,String dutyImport,String dutySignType,String shiftMode,String reminder,String defaultTime,String optionalFunction) {
                super();
                this.channelPage = channelPage;
                this.news = news;
                this.optionalHusks = optionalHusks;
                this.LeaveTypeList = LeaveTypeList;
                this.calculation = calculation;
                this.dutyImport = dutyImport;
                this.dutySignType = dutySignType;
                this.shiftMode = shiftMode;
                this.reminder = reminder;
                this.defaultTime = defaultTime;
                this.optionalFunction = optionalFunction;
        }

        public String getChannelPage() {
                return channelPage;
        }

        public void setChannelPage(String channelPage) {
                this.channelPage = channelPage;
        }

        public String getNews() {
                return news;
        }

        public void setNews(String news) {
                this.news = news;
        }

        public String getOptionalHusks() {
                return optionalHusks;
        }

        public void setOptionalHusks(String optionalHusks) {
                this.optionalHusks = optionalHusks;
        }

        public List<LeaveType> getLeaveTypeList() {
                return LeaveTypeList;
        }

        public void setLeaveTypeList(List<LeaveType> leaveTypeList) {
                LeaveTypeList = leaveTypeList;
        }

        public String getCalculation() {
                return calculation;
        }

        public void setCalculation(String calculation) {
                this.calculation = calculation;
        }

        public String getDutyImport() {
                return dutyImport;
        }

        public void setDutyImport(String dutyImport) {
                this.dutyImport = dutyImport;
        }

        public String getDutySignType() {
                return dutySignType;
        }

        public void setDutySignType(String dutySignType) {
                this.dutySignType = dutySignType;
        }

        public String getShiftMode() {
                return shiftMode;
        }

        public void setShiftMode(String shiftMode) {
                this.shiftMode = shiftMode;
        }

        public String getReminder() {
                return reminder;
        }

        public void setReminder(String reminder) {
                this.reminder = reminder;
        }

        public String getDefaultTime() {
                return defaultTime;
        }

        public void setDefaultTime(String defaultTime) {
                this.defaultTime = defaultTime;
        }

        public String getProcessSelect() {
                return processSelect;
        }

        public void setProcessSelect(String processSelect) {
                this.processSelect = processSelect;
        }

        public String getOptionalFunction() {
                return optionalFunction;
        }

        public void setOptionalFunction(String optionalFunction) {
                this.optionalFunction = optionalFunction;
        }

        @Override
        public String toString() {
                return "ZhzwItemModel{" +
                        "channelPage='" + channelPage + '\'' +
                        ", news='" + news + '\'' +
                        ", optionalHusks='" + optionalHusks + '\'' +
                        ", optionalFunction='" + optionalFunction + '\'' +
                        ", LeaveTypeList=" + LeaveTypeList +
                        ", calculation='" + calculation + '\'' +
                        ", dutyImport='" + dutyImport + '\'' +
                        ", dutySignType='" + dutySignType + '\'' +
                        ", shiftMode='" + shiftMode + '\'' +
                        ", reminder='" + reminder + '\'' +
                        ", defaultTime='" + defaultTime + '\'' +
                        ", processSelect='" + processSelect + '\'' +
                        '}';
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ZhzwItemModel)) return false;
                ZhzwItemModel that = (ZhzwItemModel) o;
                return channelPage.equals(that.channelPage) &&
                        news.equals(that.news) &&
                        optionalHusks.equals(that.optionalHusks) &&
                        LeaveTypeList.equals(that.LeaveTypeList) &&
                        calculation.equals(that.calculation) &&
                        dutyImport.equals(that.dutyImport) &&
                        dutySignType.equals(that.dutySignType) &&
                        shiftMode.equals(that.shiftMode) &&
                        reminder.equals(that.reminder);
        }


}




