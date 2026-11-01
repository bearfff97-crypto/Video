package com.ls.librarybase.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ls.librarybase.utils.StatusBarUtils;

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends AppCompatActivity {
    protected VM mViewModel;
    protected V mDataBinding;
    private ProgressBar mPrpogressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initViewModel();
        initDataBinding();
        initProgressBar();
     //   observeViewModel();


//        //开启沉浸式状态栏
//        EdgeToEdge.enable(this);
//        //mDatabinding.getRoot()获得跟布局
//        ViewCompat.setOnApplyWindowInsetsListener(mDataBinding.getRoot(), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        //开启沉浸式效果
       StatusBarUtils.setImmerseStatusBar(this);


        ARouter.getInstance().inject(this);


        initView();
        initData();

    }
    private void initProgressBar() {
        mPrpogressBar = new ProgressBar(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topToTop=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.startToStart=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom=ConstraintLayout.LayoutParams.PARENT_ID;
        mPrpogressBar.setLayoutParams(layoutParams);
        mPrpogressBar.setVisibility(View.GONE);//默认不可见
        ConstraintLayout constraintLayout=(ConstraintLayout) mDataBinding.getRoot();
        constraintLayout.addView(mPrpogressBar);
        mPrpogressBar.bringToFront();
    }


    private void initDataBinding() {
        //从子类获取的布局id
        mDataBinding = DataBindingUtil.setContentView(this,getLayoutResId());
        mDataBinding.setLifecycleOwner(this);
        //在父类中使用viewmodel在xml中的变量名，关联具体的mViewModel，效果等同于mDataBinding.setViewModel(mViewModel);
        mDataBinding.setVariable(getBindingVariableId(),mViewModel);
        //关联完mViewModel后，实时更新数据
        mDataBinding.executePendingBindings();
    }



    private void initViewModel() {
        //从子类获取viewModel赋值给mviewmodel
        mViewModel=getViewModel();
        if(mViewModel!=null){
            //控制是否显示弹窗信息；
            mViewModel.getToastText().observe(this,text->{
                Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
            });
            // 控制加载样式显示；
            mViewModel.getShowLoading().observe(this, show -> {
                mPrpogressBar.setVisibility(
                        Boolean.TRUE.equals(show) ? View.VISIBLE : View.GONE
                );
            });
            mViewModel.getFinish().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean finish) {
                    if(finish){
                        finish();
                    }
                }
            });


        }

    }


    protected abstract VM getViewModel();
    protected abstract int getLayoutResId();
    protected abstract int getBindingVariableId();
    protected abstract void initView();
    protected abstract void initData();

}
