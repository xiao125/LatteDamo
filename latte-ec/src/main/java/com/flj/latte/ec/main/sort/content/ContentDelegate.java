package com.flj.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;

import java.util.List;

import butterknife.BindView;

/**
 * 分类显示Fragment
 * Created by Administrator on 2017/9/13 0013.
 */

public class ContentDelegate  extends LatteDelegate{

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId=-1;
    private List<SectionBean> mData =null; //Bean类

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args !=null){
            mContentId = args.getInt(ARG_CONTENT_ID);
        }

    }

    public static  ContentDelegate newInstance(int contentId){

        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID,contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData(){

        RestClient.builder()
                .url("sort_content_list.php?contentId="+mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        //请求成功返回数据

                        mData = new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter =
                                new SectionAdapter(R.layout.item_section_content,
                                        R.layout.item_section_header,mData);

                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData(); //显示加载的数据



    }
}
