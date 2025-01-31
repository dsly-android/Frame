package com.htxtdshopping.htxtd.frame.widget.pickerview.utils;

import android.view.Gravity;

import com.htxtdshopping.htxtd.frame.R;

/**
 * Created by Sai on 15/8/9.
 */
public class PickerViewAnimateUtil {
    private static final int INVALID = -1;

    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the animGravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.bottom_enter : R.anim.bottom_exit;
        }
        return INVALID;
    }
}
