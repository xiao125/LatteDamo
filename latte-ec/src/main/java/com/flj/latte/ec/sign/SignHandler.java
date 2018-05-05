package com.flj.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.ec.database.DatabaseManager;
import com.flj.latte.ec.database.UserProfile;
import com.imooc.core.app.AccountManager;

/**
 * 登录管理类
 * Created by Administrator on 2017/9/14.
 */

public class SignHandler {

    public static void onSignIn(String response,ISignListener signListener){

        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        //获取到登录成功后返回的数据。并插入数据库中
        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getmDao().insert(profile);//添加数据

        //更新是否登录状态
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();



    }

    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        //获取到注册成功后的数据，并插入到数据库中
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmDao().insert(profile);

        //更新是否登录状态
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }



}
