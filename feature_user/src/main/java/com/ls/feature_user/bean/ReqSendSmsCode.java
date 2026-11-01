package com.ls.feature_user.bean;

/**
 *
 */

public class ReqSendSmsCode {
    private String mobile;//手机号
    private String event;//时间名称 一般使用手机号登陆

    public ReqSendSmsCode(String mobile, String event) {
        this.mobile = mobile;
        this.event = event;
    }
}
