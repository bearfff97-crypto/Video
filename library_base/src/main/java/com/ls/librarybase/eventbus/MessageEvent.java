package com.ls.librarybase.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * 登陆状态改变 登陆成功、退出
 */
public class MessageEvent {
    public static class LoginStatusEvent{
        private boolean isLogin;//是否登陆
        public LoginStatusEvent(boolean isLogin){
            this.isLogin=isLogin;
        }
        public boolean isLogin(){
            return isLogin;
        }
        public static void post(boolean isLogin){

            //发送一个粘性事件
            EventBus.getDefault().postSticky(new LoginStatusEvent(isLogin));
        }



    }
}
