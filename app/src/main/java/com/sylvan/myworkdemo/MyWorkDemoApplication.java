package com.sylvan.myworkdemo;

import android.app.Application;

public class MyWorkDemoApplication extends Application {
    static MyWorkDemoApplication s_ins;
    private long tick;

    public static MyWorkDemoApplication getInstance() {
        return s_ins;
    }

    public MyWorkDemoApplication() {
        s_ins = this;
    }

}
