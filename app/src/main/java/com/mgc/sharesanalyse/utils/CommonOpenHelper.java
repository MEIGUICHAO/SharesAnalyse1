package com.mgc.sharesanalyse.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Dao;

import com.mgc.sharesanalyse.entity.DaoMaster;
import com.mgc.sharesanalyse.entity.Month8Data;
import com.mgc.sharesanalyse.entity.Month8DataDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

public class CommonOpenHelper extends DaoMaster.DevOpenHelper {
    private Class[] entityClasses;

    public CommonOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, Class... classes) {
        super(context, name, factory);
        entityClasses = classes;
    }


    public CommonOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //切记不要调用super.onUpgrade(db,oldVersion,newVersion)
        if (oldVersion < newVersion) {
            for (int i = 0; i < entityClasses.length; i++) {
                MigrationHelper.migrate(db, entityClasses[i]);
            }
        }
    }
}
