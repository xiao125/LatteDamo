package com.flj.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.latte.ui.recycler.DataConverter;
import com.latte.ui.recycler.ItemType;
import com.latte.ui.recycler.MultipleFields;
import com.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getmJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i=0;i<size;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final  MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            dataList.add(entity);
            //默认设置第一个分类被选中
            dataList.get(0).setField(MultipleFields.TAG,true);

        }


        return dataList;
    }





}
