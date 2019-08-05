package com.htxtdshopping.htxtd.frame.popup_window;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BasePopupWindow;
import com.htxtdshopping.htxtd.frame.decoration.ItemDecorationDrawable;
import com.htxtdshopping.htxtd.frame.decoration.LinearDividerItemDecoration;
import com.htxtdshopping.htxtd.frame.ui.third.adapter.ImageFolderAdapter;
import com.htxtdshopping.htxtd.frame.utils.local_data.ImageFolder;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author 陈志鹏
 * @date 2018/12/11
 */
public class ImageFolderPopupWindow extends BasePopupWindow implements View.OnClickListener {

    private static final int DURATION_ANIMATION = 200;
    View mVBgColor;
    RecyclerView mRvContent;
    private List<ImageFolder> mImageFolders;
    private ImageFolderAdapter mAdapter;
    private OnImageFolderSelectedListener mListener;
    private int mSelectedPos;

    public ImageFolderPopupWindow(Context context, int height, List<ImageFolder> imageFolders,
                                  OnImageFolderSelectedListener listener) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT, height);
        mImageFolders = imageFolders;
        mListener = listener;
        mSelectedPos = 0;
        initView();
        initEvent();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.popup_image_folder;
    }

    private void initView() {
        mVBgColor = mRootView.findViewById(R.id.v_bg_color);
        mRvContent = mRootView.findViewById(R.id.rv_content);

        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContent.setHasFixedSize(true);
        mRvContent.addItemDecoration(new LinearDividerItemDecoration(mContext,
                new ItemDecorationDrawable(mContext, AutoSizeUtils.pt2px(mContext, 2), R.color._ffe0e0e0)));
        mRvContent.setAdapter(mAdapter = new ImageFolderAdapter());
    }

    private void initEvent() {
        mVBgColor.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int oldSelectedPos = mSelectedPos;
                mSelectedPos = position;
                mAdapter.setSelectedPos(mSelectedPos);
                mAdapter.notifyItemChanged(oldSelectedPos, 0);
                mAdapter.notifyItemChanged(mSelectedPos, 0);
                if (mListener != null) {
                    mListener.OnImageFolderSelected(mSelectedPos);
                }
                dismiss();
            }
        });
    }

    private void initData() {
        mAdapter.setSelectedPos(mSelectedPos);
        mAdapter.setNewData(mImageFolders);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_bg_color:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        mRvContent.post(new Runnable() {
            @Override
            public void run() {
                enterAnimator();
            }
        });
    }

    @Override
    public void dismiss() {
        exitAnimator();
    }

    private void enterAnimator() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mVBgColor, "alpha", 0, 1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRvContent, "translationY", mRvContent.getHeight(), 0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_ANIMATION);
        set.playTogether(alpha, translationY);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private void exitAnimator() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mVBgColor, "alpha", 1, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRvContent, "translationY", 0, mRvContent.getHeight());
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_ANIMATION);
        set.playTogether(alpha, translationY);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ImageFolderPopupWindow.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.start();
    }

    public interface OnImageFolderSelectedListener {
        void OnImageFolderSelected(int selectedPos);
    }
}
