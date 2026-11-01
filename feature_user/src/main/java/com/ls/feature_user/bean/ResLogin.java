package com.ls.feature_user.bean;

public class ResLogin {

    /**
     * token : 7f272840-9879-4d35-b19f-93b0feb77105
     * id : 143
     */

    private String token;//用户身份标识
    private int id;//当前用户登陆的id

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResLogin{" +
                "token='" + token + '\'' +
                ", id=" + id +
                '}';
    }
}
