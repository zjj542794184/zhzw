package com.zhzw.model;

import java.util.List;

public class LeaveType {

    //请假类型
    String leaveType;//请假类型
    //请假类型所对应得流程方式
    String leaveMode;

    public LeaveType() {
        super();
    }

    public LeaveType(String leaveType, String leaveMode) {
        super();
        this.leaveType = leaveType;
        this.leaveMode = leaveMode;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveMode() {
        return leaveMode;
    }

    public void setLeaveMode(String leaveMode) {
        this.leaveMode = leaveMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveType)) return false;
        LeaveType leaveType1 = (LeaveType) o;
        return getLeaveType().equals(leaveType1.getLeaveType()) &&
                getLeaveMode().equals(leaveType1.getLeaveMode());
    }

    @Override
    public String toString() {
        return "LeaveType{" +
                "leaveType='" + leaveType + '\'' +
                ", leaveMode='" + leaveMode + '\'' +
                '}';
    }
}
