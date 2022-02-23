package io.microshow.rxffmpeg.app.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.microshow.rxffmpeg.app.R;
import io.microshow.rxffmpeg.app.utils.Utils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 启动页
 * Created by Super on 2019/12/6.
 */
public class LaunchActivity extends BaseActivity {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //权限
    private final ActivityResultLauncher<String[]> launch = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            boolean resultBool = true;
            Set<String> keySet = result.keySet();
            for (String s : keySet) {
                Boolean value = result.get(s);
                if (!value) {
                    resultBool = false;
                    break;
                }
            }
            if (resultBool) {// 用户同意了权限
                gotoMainAct();
            } else {//用户拒绝了权限
                Toast.makeText(LaunchActivity.this, "您拒绝了权限，请往设置里开启权限", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    });

    //需要申请的权限，必须先在AndroidManifest.xml有声明，才可以动态获取权限
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_launch);

        launch.launch(PERMISSIONS_STORAGE);
    }

    private void gotoMainAct() {
        mCompositeDisposable.add(Flowable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    finish();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.fixInputMethodManagerLeak(this);
        mCompositeDisposable.clear();
    }

}
