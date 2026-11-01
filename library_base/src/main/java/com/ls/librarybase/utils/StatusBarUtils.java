package com.ls.librarybase.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 状态栏专用的工具类
 */
public class StatusBarUtils {

    /**
     * 把根布局加上状态栏的高度
     *
     * @param rootView
     */
    public static void addStatusBarHeight2RootView(View rootView) {
        // 1. 监听系统的窗口变化（当系统把状态栏加载出来时触发）
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            // 2. 获取系统栏（状态栏）的具体尺寸数据
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // 3. 把状态栏的高度设置到根布局的上方当作 Padding（内边距）
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 把对应的view加上状态栏的高度
     *
     * @param rootView
     */
    public static void addStatusBarHeight2Views(View rootView, View... views) {
        // 1. 监听系统的窗口变化（当系统把状态栏加载出来时触发）
        AtomicBoolean isNeedAddHeight = new AtomicBoolean(true);//默认需要添加高度
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            // 如果已经加过高度了，就直接跳过，不重复执行

            if (!isNeedAddHeight.get()) {
                return insets;
            }
            // 获取状态栏的高度
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 循环遍历你传进来的每一个控件
            for (int i = 0; i < views.length; i++) {
                View view = views[i];
                // 获取这个控件当前的布局参数（用来修改它的外边距）
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
// 记录它原本的顶部外边距
                int topMargin = layoutParams.topMargin;
// 核心：把“原本的上边距 + 状态栏的高度”相加，重新赋值给这个控件
                layoutParams.setMargins(0, topMargin + systemBars.top, 0, 0);
// 应用新的布局参数
                view.setLayoutParams(layoutParams);
            }
            // 关闭开关，代表高度已经加过了，下次系统刷新时不要再加了
            isNeedAddHeight.set(false);
            return insets;
        });
    }

    public static void setImmerseStatusBar(Activity activity) {
        //先把状态栏的颜色设置为透明色
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
// 2. 获取当前页面的顶级视图（DecorView）
        View decorView = activity.getWindow().getDecorView();
        // 3. 配置几个关键的显示标志位（Flags）
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |//稳定布局
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |//内容扩展到状态栏
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//指定状态栏图标深色模式
        // 4. 把这些标志应用到页面上
        decorView.setSystemUiVisibility(flags);
    }
}
