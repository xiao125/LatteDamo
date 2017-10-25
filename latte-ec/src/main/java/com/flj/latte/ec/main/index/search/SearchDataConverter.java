package com.flj.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.imooc.core.util.storage.LattePreference;
import com.latte.ui.recycler.DataConverter;
import com.latte.ui.recycler.MultipleFields;
import com.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final String jsonStr = LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY); //获取sp中的json数据
        if (!jsonStr.equals("")){ //不为空
            final JSONArray array = JSONArray.parseArray(jsonStr);//JSON串转用户组对象
            final int size = array.size();
            for (int i=0;i<size;i++){
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT,historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
