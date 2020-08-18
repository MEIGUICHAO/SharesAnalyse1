package com.mgc.sharesanalyse.base;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mgc.sharesanalyse.utils.DaoManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    static ExecutorService singlePool;
    private static App context;

    public static DaoManager getmManager() {
        return mManager;
    }

    private static DaoManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initGreenDao();
    }
    /**
     * 获取App对象
     */
    public static App getContext() {
        return context;
    }

    public static ExecutorService getSinglePool() {
        if (null == singlePool || singlePool.isShutdown() || singlePool.isTerminated()) {
            singlePool = Executors.newSingleThreadExecutor();
        }
        return singlePool;
    }
    private void initGreenDao() {
        mManager = DaoManager.getInstance();
        mManager.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
