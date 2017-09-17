package com.bansalankit.colormemory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

/**
 * This is a dialog shown after every round (selection of any 2 cards), for a brief amount of time stating the result of that round.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>29 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
public class RoundDialog extends DialogFragment {
    static final String TAG = RoundDialog.class.getSimpleName();
    private static final int DURATION = 1000;

    private DialogInterface.OnDismissListener mOnDismissListener;
    private boolean isMatched;

    RoundDialog setMatched(boolean matched) {
        isMatched = matched;
        return this;
    }

    RoundDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mOnDismissListener = listener;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() == null || getDialog().getWindow() == null) {
            dismissAllowingStateLoss();
            return null;
        }

        getDialog().getWindow().setWindowAnimations(R.style.Animation_Dialog_Zoom);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);

        View rootView = inflater.inflate(R.layout.dialog_round, container, false);
        ((ImageView) rootView.findViewById(R.id.round_result)).setImageResource(isMatched ? R.drawable.round_pass : R.drawable.round_fail);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new CountDownTimer(DURATION, DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {/*Ignored*/}

            @Override
            public void onFinish() {
                dismissAllowingStateLoss();
            }
        }.start();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mOnDismissListener.onDismiss(dialog);
    }
}
