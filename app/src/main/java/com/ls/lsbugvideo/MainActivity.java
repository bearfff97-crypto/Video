package com.ls.lsbugvideo;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.alibaba.android.arouter.launcher.ARouter;
import com.ls.feature_home.HomeFragment;
import com.ls.librarybase.base.BaseActivity;
import com.ls.librarybase.base.BaseApplication;
import com.ls.librarybase.base.BaseViewModel;
import com.ls.lsbugvideo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity <ActivityMainBinding, MainViewModel> {



    @Override
    protected MainViewModel getViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        Fragment homeFragment = (Fragment) ARouter.getInstance().build("/home/homeFragment").navigation();
        Fragment plazaFragment = (Fragment) ARouter.getInstance().build("/plaza/plazaFragment").navigation();
        Fragment findFragment = (Fragment) ARouter.getInstance().build("/find/findFragment").navigation();
        Fragment userFragment = (Fragment) ARouter.getInstance().build("/user/userFragment").navigation();
        replaceFragment(homeFragment);
        mDataBinding.rbBottomNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.rb_home){
                    replaceFragment(homeFragment);

                }else if(checkedId==R.id.rb_plaza){
                    replaceFragment(plazaFragment);

                }else if(checkedId==R.id.rb_find){
                    replaceFragment(findFragment);

                }else if(checkedId==R.id.rb_mine){
                    replaceFragment(userFragment);
            }
        }});


    }

    private void replaceFragment(Fragment homeFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fcv, homeFragment).commit();
    }

    @Override
    protected void initData() {

    }

}