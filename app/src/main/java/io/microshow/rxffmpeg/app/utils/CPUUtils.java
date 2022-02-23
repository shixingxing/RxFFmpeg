package io.microshow.rxffmpeg.app.utils;

import android.os.Build;

/**
 * Created by Super on 2020/4/16.
 */
public class CPUUtils {

    public static String getCPUAbi() {
        StringBuilder builder = new StringBuilder();
        for (String name : Build.SUPPORTED_ABIS) {
            builder.append(name);
        }
        return builder.toString();
    }

}
