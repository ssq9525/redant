package com.xby.ssq.wavlink.common.LoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xby.ssq.wavlink.R;

import java.lang.ref.WeakReference;

public class LoadingDialog extends Dialog {
    private static WeakReference<Context> weakReferenceContext = null;
    private static LoadingDialog customProgressDialog = null;

    public LoadingDialog(Context context){
        super(context);
        weakReferenceContext = new WeakReference<>(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        weakReferenceContext = new WeakReference<>(context);
    }

    public static LoadingDialog createDialog(Context context){
        weakReferenceContext = new WeakReference<>(context);
        Context context1 = weakReferenceContext.get();
        customProgressDialog = new LoadingDialog(context1, R.style.proDialog);
        customProgressDialog.setContentView(R.layout.loading_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCancelable(true);

        customProgressDialog.setCanceledOnTouchOutside(true);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public LoadingDialog setTitile(String strTitle){
        return customProgressDialog;
    }

    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public LoadingDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.state);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }

    public LoadingDialog setMessage2(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.state);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
            tvMsg.setVisibility(View.VISIBLE);
        }

        return customProgressDialog;
    }
}

