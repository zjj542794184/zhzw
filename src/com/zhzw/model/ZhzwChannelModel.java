package com.zhzw.model;
/**
 * @desc 频道页设置
 * @time 2019年6月14日
 * @author liujianghua
 */

import java.util.List;

public class ZhzwChannelModel {

    private String code; //频道code
    private String name;//频道名称
    private String describe; //频道说明
    private String display;//显示（"Y"）隐藏("N")
    private List<ZhzwChannelItemModel> items; //模块列表

    public ZhzwChannelModel() {
        super();
    }

    public ZhzwChannelModel(String code, String name, String describe, String display, List<ZhzwChannelItemModel> items) {
        super();
        this.code = code;
        this.name = name;
        this.describe = describe;
        this.display = display;
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<ZhzwChannelItemModel> getItems() {
        return items;
    }

    public void setItems(List<ZhzwChannelItemModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ChannelModel [code=" + code + ", name=" + name + ", describe=" + describe + ", display=" + display
                + ", items=" + items + "]";
    }

}

