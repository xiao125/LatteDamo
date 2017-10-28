package com.flj.latte.ec.datail;

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 动画移动轨迹
 * Created by Administrator on 2017/10/28 0028.
 */

public class ScaleUpAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {

        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target,"scaleX",08f, 1f, 1.4f, 1.2f, 1),
                ObjectAnimator.ofFloat(target,"scaleY",0.8f, 1f, 1.4f, 1.2f, 1)
        );

    }
}
