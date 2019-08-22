package com.zhzw.model;
/**
 * @desc 模块设置
 * @time 2019年6月14日
 * @author liujianghua
 */

        import java.util.List;

        public class ZhzwChannelItemModel {

            private String system; //系统号
            private String systemName; //系统名称
            private String functionDescrip; //模块功能描述
            private  String status;//模块状态0:已启用; 1:未配置完成; 2:未配置; 3:暂停应用


            public void setFunctionDescrip(String functionDescrip) {
                this.functionDescrip = functionDescrip;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getFunctionDescrip() {
                return functionDescrip;
            }

            private List<ZhzwItemModel> moduleList; //模块列表


            public ZhzwChannelItemModel() {
                super();
            }


            public ZhzwChannelItemModel(String system, String systemName, List<ZhzwItemModel> moduleList) {
                super();
                this.system = system;
                this.systemName = systemName;
                this.moduleList = moduleList;
            }


            public String getSystem() {
                return system;
            }


            public void setSystem(String system) {
                this.system = system;
            }


            public String getSystemName() {
                return systemName;
            }


            public void setSystemName(String systemName) {
                this.systemName = systemName;
            }


            public List<ZhzwItemModel> getModuleList() {
                return moduleList;
            }


            public void setModuleList(List<ZhzwItemModel> moduleList) {
            this.moduleList = moduleList;
            }


            @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((moduleList == null) ? 0 : moduleList.hashCode());
                result = prime * result + ((system == null) ? 0 : system.hashCode());
                result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
                result = prime * result + ((functionDescrip == null) ? 0 : functionDescrip.hashCode());
                result = prime * result + ((status == null) ? 0 : status.hashCode());
                return result;
            }

            @Override
            public String toString() {
                return "ZhzwChannelItemModel{" +
                        "system='" + system + '\'' +
                        ", systemName='" + systemName + '\'' +
                        ", functionDescrip='" + functionDescrip + '\'' +
                        ", status='" + status + '\'' +
                        ", moduleList=" + moduleList +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ZhzwChannelItemModel)) return false;
                ZhzwChannelItemModel that = (ZhzwChannelItemModel) o;
                return system.equals(that.system) &&
                        systemName.equals(that.systemName) &&
                        functionDescrip.equals(that.functionDescrip) &&
                        status.equals(that.status) &&
                        moduleList.equals(that.moduleList);
            }
            /*
            @Override
            public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ZhzwChannelItemModel other = (ZhzwChannelItemModel) obj;
            if (moduleList == null) {
            if (other.moduleList != null)
                return false;
            } else if (!moduleList.equals(other.moduleList))
                return false;
            if (system == null) {
            if (other.system != null)
                return false;
            } else if (!system.equals(other.system))
                return false;
            if (systemName == null) {
            if (other.systemName != null)
                return false;
            } else if (!systemName.equals(other.systemName))
                return false;
                return true;
            }
             */
        }

