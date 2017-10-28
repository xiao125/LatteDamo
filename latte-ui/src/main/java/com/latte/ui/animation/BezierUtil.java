package com.latte.ui.animation;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.latte.ui.R;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public final class BezierUtil {

    static void startAnimationForJd(final View v, int fromXDelta,
                                    int fromYDelta, int fx, int fy,
                                    int mx, int my, int tx, int ty ,
                                    final AnimationListener listener){

        final AnimationSet set = new AnimationSet(false);
        //平移动画 TranslateAnimation
        final TranslateAnimation translateAnimation1 = new TranslateAnimation(fromXDelta,mx -fx,fromYDelta,my - fy);
        translateAnimation1.setInterpolator(new DecelerateInterpolator()); //设定插值器，
        translateAnimation1.setRepeatCount(0);//设置重复次数
        translateAnimation1.setFillAfter(false);//如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        set.addAnimation(translateAnimation1); //


        final TranslateAnimation translateAnimation2 = new TranslateAnimation(fromXDelta,tx - mx,fromYDelta,ty - my);
        translateAnimation2.setInterpolator(new AccelerateInterpolator());  //设定插值器，
        translateAnimation2.setRepeatCount(0);
        translateAnimation2.setFillAfter(false);//如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        set.addAnimation(translateAnimation2);
        set.setDuration(700);
        set.setFillAfter(false);//如果设置为true，控件动画结束时，将保持动画最后时的状态
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                v.setVisibility(View.GONE);

                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        v.startAnimation(set);
    }


    static ViewGroup createAnimLayout(Activity activity){

        final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        final LinearLayout animLayout = new LinearLayout(activity);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE -1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }


    static View addViewToAnimLayout(Context mContext,View view,int[] location,boolean wrap_content){

        if (view == null) return null;
        //记录购物车坐标
        int x = location[0];
        int y = location[1];
        final LinearLayout.LayoutParams params;
        if (wrap_content){
            Drawable drawable = null;
            if (view instanceof ImageView){
                drawable = ((ImageView) view).getDrawable();
            }
            if (drawable ==null){
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }else {
                params = new LinearLayout.LayoutParams(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            }
        }else {

            final int wh = mContext.getResources().getDimensionPixelSize(R.dimen.db_goods_wh);
            params = new LinearLayout.LayoutParams(wh,wh);
        }

        params.leftMargin =x;
        params.topMargin = y;
        view.setLayoutParams(params);
        return view;

    }




    public interface AnimationListener{
        /**
         * 处理动画结束后的逻辑，不要涉及动画相关的View
         */
        void onAnimationEnd();
    }

}
