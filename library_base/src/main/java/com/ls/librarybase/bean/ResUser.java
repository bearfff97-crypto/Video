package com.ls.librarybase.bean;

public class ResUser {
    /**
     * user : {"id":143,"nickname":"151****6915","bio":"","avatar":"https://titok.fzqq.fun/uploads/20240826/50d42d478612bb3f289dd6258caa046b.jpeg","status":"normal","username":"15144326915","url":"/u/143"}
     * fans : 126
     * follow : 18
     * medal : 0
     */

    private UserInfo user;
    private int fans;//当前账户的粉丝数量
    private int follow;
    private int medal;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getMedal() {
        return medal;
    }

    public void setMedal(int medal) {
        this.medal = medal;
    }


}
