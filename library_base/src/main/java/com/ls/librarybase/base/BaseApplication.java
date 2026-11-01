package com.ls.librarybase.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;


import me.jessyan.autosize.AutoSizeConfig;

//当前工程中的Applicatrion基类
public class BaseApplication extends Application {
    private static Application instance;




    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
        if(BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        //初始化init
        ARouter.init(this);

        //AndroidAutoSize的参数初始化
        AutoSizeConfig.getInstance().setCustomFragment(true);



    }

    /**
     * 使用application生成一个全局可用的context
     * 注意不要滥用
     * 1、把Application当成是某个Activity上下文，与ui更新关联在一起，会引发错误
     * 2、更容易获取到context，会增加项目的耦合
     * @return
     */
    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
