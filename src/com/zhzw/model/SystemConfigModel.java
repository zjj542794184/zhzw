package com.zhzw.model;

/**
 * @desc 系统配置
 * @time 2019年6月17日
 * @author liujianghua
 */
public class SystemConfigModel {
    private String name;
    private String code;
    private String status;//设置状态   0:未设置   1：已设置
    private String systemName;//系统名称
    private String logo;//系统logo
    private String subjectSkin;//系统主题皮肤
    private String PageType;//版面类型

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSubjectSkin() {
        return subjectSkin;
    }

    public void setSubjectSkin(String subjectSkin) {
        this.subjectSkin = subjectSkin;
    }

    public String getPageType() {
        return PageType;
    }

    public void setPageType(String pageType) {
        PageType = pageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemConfigModel)) return false;
        SystemConfigModel that = (SystemConfigModel) o;
        return getName().equals(that.getName()) &&
                getCode().equals(that.getCode()) &&
                getStatus().equals(that.getStatus()) &&
                getSystemName().equals(that.getSystemName()) &&
                getLogo().equals(that.getLogo()) &&
                getSubjectSkin().equals(that.getSubjectSkin()) &&
                getPageType().equals(that.getPageType());
    }

    @Override
    public String toString() {
        return "SystemConfigModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", systemName='" + systemName + '\'' +
                ", logo='" + logo + '\'' +
                ", subjectSkin='" + subjectSkin + '\'' +
                ", PageType='" + PageType + '\'' +
                '}';
    }
}
