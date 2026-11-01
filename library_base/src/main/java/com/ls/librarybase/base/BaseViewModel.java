package com.ls.librarybase.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel
{

    public MutableLiveData<Integer> mErrorCode = new MutableLiveData<>();

    //如果toasttext发生变化进行弹窗显示,表示需要进行toast弹窗显示

    private MutableLiveData<String> mToastText=new MutableLiveData<>();
    private MutableLiveData<Boolean> mShowLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> mFinish=new MutableLiveData<>();
    private MutableLiveData<Boolean> mStartLoginAction = new MutableLiveData<>();
    public void startLogin() {
        mStartLoginAction.setValue(true);
    }
    public MutableLiveData<Boolean> getStartLoginAction() {
        return mStartLoginAction;
    }
    public MutableLiveData<Boolean> getFinish() {
        return mFinish;
    }
    public void onFinishPage(){
        mFinish.setValue(true);
    }

    /**
     * 显示toast弹窗
     * @param text
     */
    public void showToast(String text){
        if(text==null||text.equals("")){
            return;
        }
        mToastText.setValue(text);
        mToastText.setValue(null);
    }

    public MutableLiveData<Integer> getErrorCode() {
        return mErrorCode;
    }
    public MutableLiveData<String> getToastText() {
        return mToastText;
    }

    /**
     * 是否显示弹窗
     * @param b
     */
    public void showLoading(boolean b ){
        mShowLoading.setValue(b);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }




}
