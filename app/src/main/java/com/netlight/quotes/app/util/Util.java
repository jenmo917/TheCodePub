package com.netlight.quotes.app.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.netlight.quotes.app.R;

public class Util {
    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, (int) context.getResources().getDimension(R.dimen.margin_l));
        toast.show();
    }
}
