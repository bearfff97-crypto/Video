package com.ls.feature_user.ui.login;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ls.feature_user.bean.ResLogin;
import com.ls.librarybase.base.BaseViewModel;
import com.ls.librarybase.base.IRequestCallback;
import com.ls.librarybase.bean.ResUser;
import com.ls.librarybase.eventbus.MessageEvent;
import com.ls.network.bean.ResBase;



public class LoginViewModel extends BaseViewModel {
    private static final String TAG="LoginViewModel";
    private final LoginModel mModel;
    private MutableLiveData<String> mUserMobile= new MutableLiveData<>();//用户输入的手机号
    private MutableLiveData<String> mCode =new MutableLiveData<>();//用户输入的验证码
    private MutableLiveData<Boolean> mIsEnableLogin=new MutableLiveData<>(false);//登陆按钮是否可用,默认不可用
    private MutableLiveData<Boolean> mCheckAgreement=new MutableLiveData<>(false);//勾选协议是否可用,默认不可用
    private MutableLiveData<Boolean> mIsEnableSendCode =new MutableLiveData<>(true);//获取验证码显示文本
    private MutableLiveData<String>  mGetVerticalCodeText=new MutableLiveData<>("验证码");
    private MutableLiveData<Boolean> mLoginSuccess=new MutableLiveData<>(false);//登陆的状态是否成功
    private CountDownTimer mDownTimer;//获取验证码的倒计时
    public LoginViewModel(){

        mModel = new LoginModel();

    }


    public void updateEnableLoginBtnStatus(){
        String mobile=mUserMobile.getValue();
        String code=mCode.getValue();
        if (mobile == null || code == null) {

            return;
        }
        boolean isEnable=mobile.length()==11&&code.length()==4;
        mIsEnableLogin.setValue(isEnable);
    }

    /**
     * 发送验证发
     */
    public void sendCode(){
        String mobile=mUserMobile.getValue();
        if(mobile==null||mobile.length()!=11){
            Log.i(TAG,"sendCode:手机号不符合规则");
            showToast("请输入正确的手机号码");
            return;
        }
        //showLoading(true);
        if(mDownTimer!=null){
            mDownTimer.cancel();//防止重复点击时，没停止之前的计时
        }
        mIsEnableSendCode.setValue(false);

        // TODO 接入接口后，将下面的延时替换为验证码请求，
        // 并在请求成功和失败的回调中调用 showLoading(false)。
//        mHandler.postDelayed(() -> {
//            showLoading(false);
//
//            Log.i(TAG,"sendcode");
//        }, 1000);

        mDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //每一秒会被触发，把毫秒转为秒
                int seconds=(int)(millisUntilFinished/1000);
                mGetVerticalCodeText.setValue(seconds+"s");
            }

            @Override
            public void onFinish() {
                //倒计时完成后
                mGetVerticalCodeText.setValue("获取验证码");
                mIsEnableSendCode.setValue(true);

            }
        }.start();
        showLoading(true);
        //发起请求，让服务器发送验证码
        //发起获取验证码请求
        mModel.sendSmsCode(mobile, new IRequestCallback<ResBase<ResBase>>() {


            @Override
            public void onLoadFinish(ResBase<ResBase> datas) {
                showToast(datas.getMsg());
                showLoading(false);

            }

            @Override
            public void onLoadFailure(int errorCode, String meesage) {
                showToast(meesage);
                showLoading(false);
            }
        });
    }


    @Override
    protected void onCleared() {
      //  mHandler.removeCallbacksAndMessages(null);
        if (mDownTimer != null) {
            mDownTimer.cancel();
        }
        super.onCleared();
    }


    public void  login(){
        Log.i(TAG,"login:");
        Boolean checkAgreement = mCheckAgreement.getValue();
        if(!checkAgreement){
            showToast("请先同意用户协议与隐私政策");

            Log.i(TAG,"请先同意用户协议与隐私政策");
            return;
        }
        showLoading(true);
       // showToast("正在登录...");
        Log.i(TAG,"login:");
        String mobile = mUserMobile.getValue();
        String code = mCode.getValue();

        mModel.mobileLogin(mobile, code, new IRequestCallback<ResBase<ResLogin>>() {
            @Override
            public void onLoadFinish(ResBase<ResLogin> datas) {
                Log.i(TAG,"onLoadFinish token:"+datas.getData());
                showLoading(false);
                showToast(datas.getMsg());
                int id = datas.getData().getId();
                getUserInfo(id);
            }

            @Override
            public void onLoadFailure(int errorCode, String meesage) {
                showLoading(false);
                showToast(meesage);
            }
        });

    }

    private void getUserInfo(int id) {
        showLoading(true);
        mModel.getUserInfo(String.valueOf(id), new IRequestCallback<ResBase<ResUser>>() {
            @Override
            public void onLoadFinish(ResBase<ResUser> datas) {


                showLoading(false);
                mLoginSuccess.setValue(true);
                //发送一个已登录的状态
                MessageEvent.LoginStatusEvent.post(true);
                   }

            @Override
            public void onLoadFailure(int errorCode, String meesage) {
                showLoading(false);
                showToast(meesage);
            }
        });
    }

    public MutableLiveData<Boolean> getIsEnableLogin() {
        return mIsEnableLogin;
    }

    public MutableLiveData<String> getUserMobile() {
        return mUserMobile;
    }

    public MutableLiveData<String> getCode() {
        return mCode;
    }

    public MutableLiveData<Boolean> getCheckAgreement() {
        return mCheckAgreement;
    }

    public MutableLiveData<Boolean> getIsEnableSendCode() {
        return mIsEnableSendCode;
    }

    public MutableLiveData<String> getGetVerticalCodeText() {
        return mGetVerticalCodeText;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return mLoginSuccess;
    }
}
