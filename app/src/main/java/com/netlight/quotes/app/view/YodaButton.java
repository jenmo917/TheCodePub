package com.netlight.quotes.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.netlight.quotes.app.R;

public class YodaButton extends ImageButton {

    public YodaButton(Context context) {
        super(context);
        init();
    }

    public YodaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YodaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMaterialAttributes();
        setDefaultPadding();
        applyStyle(R.drawable.image_yoda);
    }

    private void setDefaultPadding() {
        int leftRightPadding = (int) getResources().getDimension(R.dimen.margin_l);
        int topBottomPadding = (int) getResources().getDimension(R.dimen.margin_l);
        setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
    }

    private void applyStyle(int backgroundDrawableResource) {
        Drawable backgroundDrawable = addRippleEffectToDrawable(ContextCompat.getDrawable(getContext(), backgroundDrawableResource));
        setBackground(backgroundDrawable);
    }

    private Drawable addRippleEffectToDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getRippleDrawable(getContext(), drawable);
        }
        return drawable;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getRippleDrawable(Context context, Drawable drawable) {
        int[][] states = new int[][]{
                new int[]{}
        };
        int[] colors = new int[]{context.getResources().getColor(R.color.selected)};
        ColorStateList myList = new ColorStateList(states, colors);
        return new RippleDrawable(myList, drawable, drawable);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setMaterialAttributes() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
    }
}