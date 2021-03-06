package com.flj.latte.ec.datail;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.imooc.core.util.log.LatteLogger;
import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.animation.BezierAnimation;
import com.latte.ui.animation.BezierUtil;
import com.latte.ui.banner.HolderCreator;
import com.latte.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class GoodsDetailDelegate extends LatteDelegate implements AppBarLayout.OnOffsetChangedListener,
        BezierUtil.AnimationListener{

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart) //购物车数量标识
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;

    private String mGoodsThumbUrl = null;
    private int mShopCount = 0; //购物车数量


    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100,100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart(){

        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg); //显示图片

        /**参数：
         * 上下文
         * 开始
         * 结束
         * 动画显示的imagev
         * 监听回调
         */
        BezierAnimation.addCart(this,mRlAddShopCart,mIconShopCart,animImg,this); //显示加入购物车轨迹


    }

    public static GoodsDetailDelegate create(int goodsId){

        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID,goodsId);
        final GoodsDetailDelegate detailDelegate = new GoodsDetailDelegate();
        detailDelegate.setArguments(args);
        return detailDelegate;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args !=null){
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE); //toobar折叠后颜色
        mAppBar.addOnOffsetChangedListener(this);
        mCircleTextView.setCircleBackground(Color.RED); //设置提醒小圆圈颜色

        initData(); //请求详细页面数据

        initTabLayout();

    }

    private void initTabLayout(){
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.app_main));//设置被选中标签下方导航条颜色
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));// //设置标签的字体颜色，1为未选中标签的字体颜色，2为被选中标签的字体颜色
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private void initData(){

        RestClient.builder()
                .url("goods_detail.php")
                .params("goods_id",mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");

                        initBanner(data); //设置Banner
                        initGoodsInfo(data); //设置说明.添加fragment

                        initPager(data); //Tabyout apadter

                        setShopCartCount(data); //显示该商品在购物车的数量



                   }
                })
                .build()
                .get();


    }

    private void initBanner(JSONObject data){

        final JSONArray array = data.getJSONArray("banners");//解析json
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i=0;i<size;i++){
            images.add(array.getString(i));
        }

        //设置轮播图
        mBanner.setPages(new HolderCreator(),images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);



    }

    private void initGoodsInfo(JSONObject data) {
        final String goodsData = data.toJSONString();
        getSupportDelegate().
                loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(goodsData));
    }


    private void initPager(JSONObject data){
        // tabyout  pageAdapter
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(),data);
        mViewPager.setAdapter(adapter);

    }


    private void setShopCartCount(JSONObject data){

        mGoodsThumbUrl = data.getString("thumb");//获得商品icon url
        if (mShopCount ==0){
            mCircleTextView.setVisibility(View.GONE); //如果没有加入购物车就隐藏
        }


    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onAnimationEnd() {

        //执行加入购物车动画
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);

        //请求服务端添加购物车数量
        RestClient.builder()
                .url("add_shop_cart_count.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LatteLogger.json("ADD", response);
                        final boolean isAdded = JSON.parseObject(response).getBoolean("data");
                        if (isAdded){  //添加成功
                            mShopCount++; //数量增加
                            mCircleTextView.setVisibility(View.VISIBLE);
                            mCircleTextView.setText(String.valueOf(mShopCount)); //显示购物车数量
                        }
                    }
                })
                .params("count",mShopCount)
                .build()
                .post();

    }
}
