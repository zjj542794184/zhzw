package com.zhzw.model;

public class ChannelModel {

    String name;//频道页name
    String code;//频道页code
    String type;//应用登录人类型  0：系统管理员   1：审计管理    2：用户管理

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChannelModel)) return false;
        ChannelModel that = (ChannelModel) o;
        return getName().equals(that.getName()) &&
                getCode().equals(that.getCode()) &&
                getType().equals(that.getType());
    }

    @Override
    public String toString() {
        return "ChannelModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
