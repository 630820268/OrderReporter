package com.example.administrator.orderreporter.net.reportnet;

/**
 * Created by Administrator on 2017/12/27.
 */

public class ReportResponse {
    private int errorCode;

    private String errorMsg;

    private String data;

//    private String responseBizData;
//    private String errDesc;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response:["+"returnMsg:"+errorMsg+","+"code:"+errorCode;

    }
}
