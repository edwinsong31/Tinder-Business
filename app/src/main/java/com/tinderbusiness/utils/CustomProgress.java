package com.tinderbusiness.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.tinderbusiness.R;


public class CustomProgress extends Dialog {

    private CustomProgress dialog;

    public CustomProgress(Context context) {
        super(context);
        dialog = new CustomProgress(context, R.style.CustomProgress);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus) {


    }


    public CustomProgress show(Context context, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {

        if (dialog == null)
            dialog = new CustomProgress(context, R.style.CustomProgress);

        dialog.setTitle("");
        dialog.setContentView(R.layout.custom_progress);


        TextView tvMessage = dialog.findViewById(R.id.message);

        tvMessage.setText(message);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
        }


        if (!((Activity) context).isFinishing()) {
            //show dialog
            dialog.show();
        }


        return dialog;
    }

    public void ProgressDismiss() {

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

    }
}
