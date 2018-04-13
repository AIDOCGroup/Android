package me.aidoc.client.mvp.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SlideInBottomAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
