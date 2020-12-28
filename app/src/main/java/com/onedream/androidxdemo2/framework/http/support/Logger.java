package com.onedream.androidxdemo2.framework.http.support;


import com.onedream.androidxdemo2.framework.utils.system.LogHelper;

public class Logger implements LoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        LogHelper.e("ATU Logger", "http : " + message);
    }
}
