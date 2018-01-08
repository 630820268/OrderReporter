package com.example.administrator.orderreporter.base.bean;

/**
 * Created by Administrator on 2017/12/27.
 */

public class UserInfo {
    private String userid;

    private String verify;

    private String compId;

    private String compName;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
}
