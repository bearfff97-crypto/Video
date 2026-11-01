package com.ls.librarybase.base;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ls.librarybase.R;
import com.ls.librarybase.config.ARouterPath;
import com.ls.network.config.ErrorStatusConfig;
import com.ls.librarybase.databinding.LayoutStatusViewBinding;

public abstract class BaseFragment<V extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {
    protected VM mViewModel;
    protected V mDataBinding;

    protected LayoutStatusViewBinding mLayoutStatusViewBinding;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding= DataBindingUtil.inflate(inflater,getLayoutResId(),container,false);
        mDataBinding.setLifecycleOwner(this);

        VM viewModel=getViewModel();
        if(viewModel!=null){
            mViewModel=viewModel;
        }

        //在父类中使用viewmodel在xml中的变量名 关联具体的mViewModel  效果等同于mDataBinding.setViewModel(mViewModel);
        int bindingVariabled=getBindingVariableId();
        if(bindingVariabled!=0){
            mDataBinding.setVariable(bindingVariabled,mViewModel);
            mDataBinding.executePendingBindings();
        }
        ARouter.getInstance().inject(this);

        initView();
        initData();
        initStatusView();
        initProgressBar();
        initToast();
        if (mViewModel != null) {
            mViewModel.getStartLoginAction().observe(requireActivity(), aBoolean -> {
                //true表示需要打开登录页
                if (aBoolean) {
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_LOGIN).navigation();
                }
            });
        }
        return mDataBinding.getRoot();
    }

    private void initProgressBar() {
        mProgressBar = new ProgressBar(getContext());
        if (mDataBinding.getRoot() instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            mProgressBar.setLayoutParams(layoutParams);
            mProgressBar.setVisibility(View.GONE);//默认不可见
            ConstraintLayout constraintLayout = (ConstraintLayout) mDataBinding.getRoot();
            constraintLayout.addView(mProgressBar);
        }
//        mProgressBar.bringToFront();
    }
    private void initToast() {
        if(mViewModel!=null){
            mViewModel.getToastText().observe(getViewLifecycleOwner(),text->{
                if(text!=null&& !text.isEmpty()){
                    Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
                }


            });
            //控制加载样式是否显示
            mViewModel.getShowLoading().observe(getViewLifecycleOwner(),show -> {
            //    mProgressBar.setVisibility(Boolean.TRUE.equals(show) ? View.VISIBLE : View.GONE);
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }

    }

    private void initStatusView() {
        ViewGroup root =(ViewGroup) mDataBinding.getRoot();
        mLayoutStatusViewBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.layout_status_view, null, false);
        View statusViewRoot = mLayoutStatusViewBinding.getRoot();
        statusViewRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.addView(mLayoutStatusViewBinding.getRoot());
        if(mViewModel!=null){


        //错误码状态显示
        //getViewLifecycleOwner（）；通过这个方法获取当前视图声明周期的所有者，以免造成内存泄露
        mViewModel.getErrorCode().observe(getViewLifecycleOwner(),errorCode->{
            String content="";
            switch (errorCode){
                case ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL:
                    content="网络错误，请检查网络";
                    break;
                case ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN:
                    content="请登陆后再进行操作";
                    break;
                case ErrorStatusConfig.ERROR_STATUS_EMPTY:
                    content="当前没有更多数据";
                    break;
                case ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR:
                    content="服务器异常";
                    break;
                case ErrorStatusConfig.ERROR_STATUS_NORMAL:
                default:
                    content="";
                    break;
                //
            }

            LayoutStatusViewBinding layoutStatusView =mLayoutStatusViewBinding;
            boolean isVisibility = errorCode!=ErrorStatusConfig.ERROR_STATUS_NORMAL;
            layoutStatusView.clStatusView.setVisibility(isVisibility?View.VISIBLE:View.GONE);
            layoutStatusView.tvLable.setText(content);

        });}

    }


    protected abstract VM getViewModel();
    protected abstract int getLayoutResId();
    protected abstract int getBindingVariableId();

    protected abstract void initView();//如果子类需要做一些视图上的初始化

    protected abstract void initData();//如果子类需要做一些数据上的初始化

}
