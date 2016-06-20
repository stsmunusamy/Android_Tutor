package com.myapps35.tutorial.Utils.FabArcMenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.myapps35.tutorial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saurabh on 14/12/15.
 */
@CoordinatorLayout.DefaultBehavior(MoveUpwardBehaviour.class)
public class ArcMenu extends FrameLayout {

    private static final double POSITIVE_QUADRANT = 90;
    private static final double NEGATIVE_QUADRANT = -90;
    private static final double ANGLE_FOR_ONE_SUB_MENU = 0;
    private static final int ANIMATION_TIME = 300; //This time is in milliseconds

    FloatingActionButton fabMenu;
    Drawable mDrawable;
    ColorStateList mColorStateList;
    int mRippleColor;
    long mAnimationTime;
    float mCurrentRadius, mFinalRadius, mElevation;
    int menuMargin;
    boolean mIsOpened = false;
    double mQuadrantAngle;
    MenuSideEnum mMenuSideEnum;
    int cx, cy; //Represents the center points of the circle whose arc we are considering
    private StateChangeListener mStateChangeListener;

    public ArcMenu(Context context) {
        super(context);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu, 0, 0);
        init(attr);
        fabMenu = new FloatingActionButton(context);
    }

    private void init(TypedArray attr) {
        Resources resources = getResources();

        mDrawable = attr.getDrawable(R.styleable.ArcMenu_menu_scr);
        mColorStateList = attr.getColorStateList(R.styleable.ArcMenu_menu_color);
        mFinalRadius = attr.getDimension(R.styleable.ArcMenu_menu_radius, resources.getDimension(R.dimen.default_radius));
        mElevation = attr.getDimension(R.styleable.ArcMenu_menu_elevation, resources.getDimension(R.dimen.default_elevation));
        mMenuSideEnum = MenuSideEnum.fromId(attr.getInt(R.styleable.ArcMenu_menu_open, 0));
        mAnimationTime = attr.getInteger(R.styleable.ArcMenu_menu_animation_time, ANIMATION_TIME);
        mCurrentRadius = 0;

        if(mDrawable == null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mDrawable = resources.getDrawable(android.R.drawable.ic_dialog_email, null);
            else
                mDrawable = resources.getDrawable(android.R.drawable.ic_dialog_email);
        }

        mRippleColor = attr.getColor(R.styleable.ArcMenu_menu_ripple_color, getThemeAccentColor(getContext(), R.attr.colorControlHighlight));

        if(mColorStateList == null) {
            mColorStateList = ColorStateList.valueOf(getThemeAccentColor(getContext(), R.attr.colorAccent));
        }

        if(mMenuSideEnum == MenuSideEnum.ARC_LEFT)
            mQuadrantAngle = POSITIVE_QUADRANT;
        else
            mQuadrantAngle = NEGATIVE_QUADRANT;

        menuMargin = attr.getDimensionPixelSize(R.styleable.ArcMenu_menu_margin, resources.getDimensionPixelSize(R.dimen.fab_margin));
    }

    /**
     * Helper method to get theme related attributes
     * @param context
     * @param resId
     * @return
     */
    private int getThemeAccentColor(Context context, @AttrRes int resId) {
        TypedValue value = new TypedValue ();
        context.getTheme().resolveAttribute(resId, value, true);
        return value.data;
    }

    private void addMainMenu() {
        fabMenu.setImageDrawable(mDrawable);
        fabMenu.setBackgroundTintList(mColorStateList);
        fabMenu.setOnClickListener(mMenuClickListener);
        fabMenu.setRippleColor(mRippleColor);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            fabMenu.setElevation(mElevation);

        addView(fabMenu);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutMenu();
        layoutChildren();
    }

    private void layoutChildren() {
        layoutChildrenArc();
    }

    private void layoutChildrenArc() {
        int childCount = getChildCount();
        double eachAngle = getEachArcAngleInDegrees();

        int leftPoint, topPoint, left, top;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child == fabMenu || child.getVisibility() == GONE)
                continue;
            else {
                double totalAngleForChild = eachAngle * (i);
                leftPoint = (int) (mCurrentRadius * Math.cos(Math.toRadians(totalAngleForChild)));
                topPoint = (int) (mCurrentRadius * Math.sin(Math.toRadians(totalAngleForChild)));

                if(mMenuSideEnum == MenuSideEnum.ARC_RIGHT) {
                    left = cx + leftPoint;
                    top = cy + topPoint;
                }
                else {
                    left = cx - leftPoint;
                    top = cy - topPoint;
                }

                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }

    /**
     * Lays out the main fabMenu on the screen.
     * Currently, the library only supports laying out the menu on the bottom right or bottom left of the screen.
     * The proper layout position is directly dependent on the which side the radial arch menu will be show.
     *
     */
    //TODO: work on fixing this
    private void layoutMenu() {
        if(mMenuSideEnum == MenuSideEnum.ARC_RIGHT) {
            cx = 0 + menuMargin;
            cy = getMeasuredHeight() - fabMenu.getMeasuredHeight() - menuMargin;
        }

        else {
            cx = getMeasuredWidth() - fabMenu.getMeasuredWidth() - menuMargin;
            cy = getMeasuredHeight() - fabMenu.getMeasuredHeight() - menuMargin;
        }

        fabMenu.layout(cx, cy, cx + fabMenu.getMeasuredWidth(), cy + fabMenu.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //The main menu is added as the last child of the view.
        addMainMenu();
        toggleVisibilityOfAllChildViews(mIsOpened);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(fabMenu, widthMeasureSpec, heightMeasureSpec);
        int width = fabMenu.getMeasuredWidth();
        int height = fabMenu.getMeasuredHeight();

        boolean accommodateRadius = false;
        int maxWidth, maxHeight;
        maxHeight = maxWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child == fabMenu || child.getVisibility() == GONE)
                continue;
            else {
                accommodateRadius = true;
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                //maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                //maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            }
        }

        if(accommodateRadius) {
            int radius = Math.round(mCurrentRadius);
            width+=(radius + maxWidth);
            height+=(radius + maxHeight);
        }

        width+= menuMargin;
        height+= menuMargin;
        setMeasuredDimension(width, height);
    }

    /**
     * The number of menu items is the number of menu options added by the user.
     * This is 1 less than the total number of child views, because we manually add one view to the viewgroup which acts as the main menu.
     * @return
     */
    private int getSubMenuCount() {
        return getChildCount() - 1;
    }

    /**
     * If there is only onle sub-menu, then it wil be placed at 45 degress.
     * For the rest, we use 90/(n-1), where n is the number of sub-menus;
     * @return
     */
    private double getEachArcAngleInDegrees() {
        if(getSubMenuCount() == 1)
            return ANGLE_FOR_ONE_SUB_MENU;
        else
            return mQuadrantAngle / ((double) getSubMenuCount() - 1);
    }

    private void toggleVisibilityOfAllChildViews(boolean show) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(child == fabMenu)
                continue;

            if(show)
                child.setVisibility(VISIBLE);
            else
                child.setVisibility(GONE);
        }
    }

    private OnClickListener mMenuClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleMenu();
        }
    };

    private void beginOpenAnimation() {
        ValueAnimator openMenuAnimator = ValueAnimator.ofFloat(0, mFinalRadius);
        openMenuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRadius = (float) animation.getAnimatedValue();
                requestLayout();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());

        List<Animator> animationCollection = new ArrayList<>(getSubMenuCount() + 1);
        animationCollection.add(openMenuAnimator);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view == fabMenu)
                continue;

            animationCollection.add(ObjectAnimator.ofFloat(view, "scaleX", 0, 1));
            animationCollection.add(ObjectAnimator.ofFloat(view, "scaleY", 0, 1));
            animationCollection.add(ObjectAnimator.ofFloat(view, "alpha", 0, 1));
        }

        animatorSet.playTogether(animationCollection);
        animatorSet.setDuration(mAnimationTime);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toggleVisibilityOfAllChildViews(mIsOpened);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mStateChangeListener!=null)
                    mStateChangeListener.onMenuOpened();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private void beginCloseAnimation() {
        ValueAnimator closeMenuAnimator = ValueAnimator.ofFloat(mFinalRadius, 0);
        closeMenuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRadius = (float) animation.getAnimatedValue();
                requestLayout();
            }
        });

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        List<Animator> animationCollection = new ArrayList<>(getSubMenuCount() + 1);
        animationCollection.add(closeMenuAnimator);

        AnimatorSet rotateAnimatorSet = new AnimatorSet();
        rotateAnimatorSet.setInterpolator(new AccelerateInterpolator());
        List<Animator> rotateAnimationCollection = new ArrayList<>(getSubMenuCount());

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view == fabMenu)
                continue;

            animationCollection.add(ObjectAnimator.ofFloat(view, "scaleX", 1, 0));
            animationCollection.add(ObjectAnimator.ofFloat(view, "scaleY", 1, 0));
            animationCollection.add(ObjectAnimator.ofFloat(view, "alpha", 1, 0));

            rotateAnimationCollection.add(ObjectAnimator.ofFloat(view, "rotation", 0, 360));
        }

        animatorSet.playTogether(animationCollection);
        animatorSet.setDuration(mAnimationTime);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                toggleVisibilityOfAllChildViews(mIsOpened);
                if(mStateChangeListener!=null)
                    mStateChangeListener.onMenuClosed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        rotateAnimatorSet.playTogether(rotateAnimationCollection);
        rotateAnimatorSet.setDuration(mAnimationTime/3);
        rotateAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        rotateAnimatorSet.start();
    }

    //ALL API Calls
    public void toggleMenu() {
        mIsOpened = !mIsOpened;
        if(mIsOpened)
            beginOpenAnimation();
        else
            beginCloseAnimation();
    }

    public boolean isMenuOpened() {
        return mIsOpened;
    }

    public void setAnimationTime(long animationTime) {
        mAnimationTime = animationTime;
    }

    public void setStateChangeListener(StateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
    }

    @SuppressWarnings("unused")
    public void setRadius(float radius) {
        this.mFinalRadius = radius;
        invalidate();
    }
}


/*
     Arc FAB Menu declaration TYPE - 1
    ===================================

    <com.sa90.materialarcmenu.ArcMenu
        android:id="@+id/arcMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        app:menu_scr="@drawable/ic_dialog_dialer"
        app:menu_open="arc_left">

        <android.support.design.widget.FloatingActionButton
            android:id="@+id/fab1"
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_email"
            android:layout_height="wrap_content" />

        <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_alert"
            android:layout_height="wrap_content" />

        <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_info"
            android:layout_height="wrap_content" />

        <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_map"
            android:layout_height="wrap_content" />

    </com.sa90.materialarcmenu.ArcMenu>




     Arc FAB Menu declaration TYPE - 2
    ===================================

    <com.sa90.materialarcmenu.ArcMenu
        android:id="@+id/arcMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|start"
        app:menu_color="@color/colorPrimaryDark"
        app:menu_radius="200dp"
        app:menu_ripple_color="@color/darker_gray"
        app:menu_scr="@drawable/ic_dialog_dialer"
        app:menu_open="arc_right">

        <ImageButton
            android:id="@+id/ib1"
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_email"
            android:layout_height="wrap_content" />

        <ImageButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_alert"
            android:layout_height="wrap_content" />

        <ImageButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_info"
            android:layout_height="wrap_content" />

        <ImageButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_map"
            android:layout_height="wrap_content" />

        <ImageButton
            android:layout_width="wrap_content"
            android:src="@drawable/ic_dialog_dialer"
            android:layout_height="wrap_content" />

    </com.sa90.materialarcmenu.ArcMenu>




    Customization

Currently the library offers the following customization options:
====================================================================

menu_scr: Controls the FAB Menu's image
menu_color: Controls the background color of the FAB Menu. Default to the colorAccent
menu_ripple_color: Controls the ripple color of the FAB Menu. Defaults to colorControlHighlight
menu_radius: Controls the radius of the arc
menu_elevation: Controls elevation (shadow cast) of the FAB Menu. Default to 6dp.
menu_margin: Controls the margin applied to the FAB Menu. Currently the same margin is applied to all four corners (defaults to 16dp).
menu_open: Controls which side of the FAB menu is the arc menu displayed on. Currently supports one of arc_left or arc_right
menu_animation_time: Controls the animation time to transition the menu from close to open state and vice versa. The time is represented in milli-seconds
API



Currently the library offers the following API's
=================================================

toggleMenu: Open or close the menu depending on its current state.
setRadius: Set the radius of the arc menu.
isMenuOpened: Returns whether the menu is opened or closed.
setAnimationTime: Controls the animation time to transition the menu from close to open state and vice versa. The time is represented in milli-seconds
setStateChangeListener: Allows you to listen to the state changes of the Menu, i.e. onMenuOpened and onMenuClosed events


* */