package com.mgc.sharesanalyse.base;

import android.app.Application;

import com.mgc.sharesanalyse.utils.DaoManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }
}
