package com.imooc.core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.imooc.core.R;
import com.imooc.core.R2;
import com.imooc.core.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;//总数
    private int mIndexDelegate =0;//当前下标
    private int mClickedColor= Color.RED;//当前选择后的颜色

    @BindView(R2.id.bottom_bar) //底部布局控件
    LinearLayoutCompat mBottomBar = null;

    //LinkedHashMap读取数据的顺序是根据写入的顺序
    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract  int setClickedColor();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() !=0){
            mClickedColor =setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = setItems(builder); //映射到ItemBuilder
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean,BottomItemDelegate> item : ITEMS.entrySet()){
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);//添加BottomItem
        }


    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final int size = ITEMS.size();
        for (int i=0; i<size;i++){
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);

            //设置每个item的标识
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0); //itemIcon
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1); //textview
            final BottomTabBean bean = TAB_BEANS.get(i); //BottomTabBean

            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i==mIndexDelegate){
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);

            }
        }

        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        //加载多个同级根Fragment,类似Wechat, QQ主页的场景
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);


    }

    private void resetColor(){
        final int count = mBottomBar.getChildCount();
        for (int i=0;i<count;i++){

            final RelativeLayout item= (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }


    @Override
    public void onClick(View v) {

        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));

        //注意先后顺序
        mCurrentDelegate = tag;

    }




}
