package io.microshow.rxffmpeg.app;

import android.app.Application;

import io.microshow.rxffmpeg.BuildConfig;
import io.microshow.rxffmpeg.RxFFmpegInvoke;
import io.microshow.rxffmpeg.app.utils.UmengHelper;

/**
 * Application
 * Created by Super on 2018/12/7.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UmengHelper.initUMConfigure(this);

        //开启debug模式，正式环境设为false即可
        RxFFmpegInvoke.getInstance().setDebug(BuildConfig.DEBUG);

    }
}
