package com.netlight.quotes.app.view.custom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.netlight.quotes.app.R;

public class DeleteDialog extends DialogFragment {

    private OnSuccessListener onSuccessListener;
    private Button button;

    public interface OnSuccessListener {
        void onSuccess();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.dialog_delete, container);
        findViews(fragmentView);
        setOnButtonClickListner();
        return fragmentView;
    }

    private void setOnButtonClickListner() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSuccessListener != null) {
                    onSuccessListener.onSuccess();
                }
                dismiss();
            }
        });
    }

    private void findViews(View fragmentView) {
        button = (Button) fragmentView.findViewById(R.id.deleteButton);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((int) getResources().getDimension(R.dimen.delete_dialog_width), (int) getResources().getDimension(R.dimen.delete_dialog_height));
        window.setGravity(Gravity.CENTER);
    }

    public static void showDialog(FragmentManager fragmentManager, DeleteDialog deleteFragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(deleteFragment.getClass().getName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        deleteFragment.show(ft, deleteFragment.getClass().getName());
    }

    public void setOnSuccessAndCloseListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}
