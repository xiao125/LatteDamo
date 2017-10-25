package com.flj.latte.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.imooc.core.util.storage.LattePreference;
import com.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class SearchDelegate extends LatteDelegate {


    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchEdit = null;

    @OnClick(R2.id.tv_top_search)
    void onCliclSearch(){ //搜索

        RestClient.builder()
                .url("search.php?key=")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final String searchItemText = mSearchEdit.getText().toString();
                        saveItem(searchItemText); //存储数据
                        mSearchEdit.setText("");
                        //展示一些东西
                        //弹出一段话
                    }
                })
                .build()
                .get();

    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack(){

        getSupportDelegate().pop();
    }


    @SuppressWarnings("unchecked")
    private void saveItem(String item){

        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)){ //输入框不为空
            List<String> history;
            //获取sp中数据
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);

            if (StringUtils.isEmpty(historyStr)){  //如果没有sp没有数据
                history = new ArrayList<>();
            }else {

                history = JSON.parseObject(historyStr,ArrayList.class);//JSON串转用户组对象
            }

            history.add(item); //添加数据
            final String json = JSON.toJSONString(history);
            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY,json); //sp存入数据


        }

    }





    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        //展示搜索记录
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(adapter);

        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() { //分割线
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });

        mRecyclerView.addItemDecoration(itemDecoration);


    }
}
