package com.flj.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库管理类
 * Created by Administrator on 2017/9/14.
 */

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;

    public DatabaseManager() {

    }

    //初始化数据库
    public DatabaseManager init(Context context){

        initDao(context);
        return this;

    }

    //内部类单例
    private static final class Holder{
        private static final DatabaseManager INSTANCE = new DatabaseManager();

    }

    public static DatabaseManager getInstance(){
       return Holder.INSTANCE;

    }


    private void initDao(Context context){

        //设置数据库名
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getmDao(){
        return mDao;
    }


}
